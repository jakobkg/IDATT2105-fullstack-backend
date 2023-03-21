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

    private static final String issuer_id = "dritfraloftet.no";
    private static final Duration TOKEN_VALIDITY = Duration.ofDays(7);

    Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

    @PostMapping(value = "")
    @ResponseStatus(value = HttpStatus.CREATED)
    public String getToken(final @RequestBody LoginRequest request) {
        User user = userRepository.findByEmail(request.email()).orElseThrow(() -> {
            return new ResponseStatusException(HttpStatus.NOT_FOUND);
        });

        if (new BCryptPasswordEncoder().matches(request.password(), user.getPassword())) {
            return generateToken(user);
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    private static String generateToken(final User user) {
        final Instant now = Instant.now();
        final Algorithm algorithm = Algorithm.HMAC512(TokenController.secret);

        String result = JWT.create()
                .withSubject(user.getEmail())
                .withClaim("type", user.getType().toString())
                .withIssuer(TokenController.issuer_id)
                .withIssuedAt(now)
                .withExpiresAt(now.plus(TokenController.TOKEN_VALIDITY))
                .sign(algorithm);

        return result;
    }
}
