package edu.ntnu.jakobkg.idatt2105projbackend.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import ch.qos.logback.classic.Logger;
import edu.ntnu.jakobkg.idatt2105projbackend.model.LoginRequest;
import edu.ntnu.jakobkg.idatt2105projbackend.model.User;
import edu.ntnu.jakobkg.idatt2105projbackend.repo.UserRepository;

import java.time.Duration;
import java.time.Instant;

@RestController
@RequestMapping(value = "/login")
@EnableAutoConfiguration
@CrossOrigin
public class TokenController {

    @Autowired
    private UserRepository userRepository;
    
    public static final String secret = "superduperhemmelig"; // TODO: last fra environment variable

    private static final String issuer_id = "Jakob, Sondre, Ingrid og Kasia AS";

    //  Every token is valid for one week before requiring users to re-authenticate
    private static final Duration TOKEN_VALIDITY = Duration.ofDays(7);

    Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    /**
     * Get a JWT
     * 
     * This endpoint receives an email address and a password, and responds with
     * a fresh JWT if the log-in attempt was successful
     * 
     * @param request - a request body containing `email` and `password` fields
     * @return a JWT if log-in was successful, HTTP 403 otherwise
     */
    @PostMapping(value = "")
    @ResponseStatus(value = HttpStatus.CREATED)
    public String getToken(final @RequestBody LoginRequest request) {
        // First, check if the user trying to log in exists
        User user = userRepository.findByEmail(request.email()).orElseThrow(() -> {
            // If not, respond with HTTP 403
            return new ResponseStatusException(HttpStatus.FORBIDDEN);
        });

        // If the user exists, verify that the provided password is correct
        if (new BCryptPasswordEncoder().matches(request.password(), user.getPassword())) {
            // If the password is correct, generate a token and respond with it
            return generateToken(user);
        }

        // If the password is not correct, reply with HTTP 403
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    /**
     * This function generates a JWT for a given user, signed with HMAC512
     * @param user - The user for whom to generate a token
     * @return a String containing the generated JWT
     */
    private static String generateToken(final User user) {
        final Instant now = Instant.now();
        final Algorithm algorithm = Algorithm.HMAC512(TokenController.secret);

        String result = JWT.create()
                .withSubject(user.getEmail())
                .withClaim("type", user.getType().toString())
                .withClaim("id", user.getId().toString())
                .withIssuer(TokenController.issuer_id)
                .withIssuedAt(now)
                .withExpiresAt(now.plus(TokenController.TOKEN_VALIDITY))
                .sign(algorithm);

        return result;
    }
}
