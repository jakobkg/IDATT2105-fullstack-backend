package edu.ntnu.jakobkg.idatt2105projbackend.Security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import edu.ntnu.jakobkg.idatt2105projbackend.controller.TokenController;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * Authentication filter
 * 
 * The `doFilterInternal` method of this class is called
 * once per request the server receives, and provides
 * authentication context to request handlers that come after
 * it in the request handling chain
 */
@Controller
public class AuthFilter extends OncePerRequestFilter {

    private static Logger logger = LogManager.getLogger(AuthFilter.class);

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // First, check if an authorization header was provided at all
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            // If not, there is no authentication to be done and we return early
            filterChain.doFilter(request, response);
            return;
        }

        // We then validate the provided JWT, and get the encoded email
        String token = header.substring(7);
        final String username = validateTokenAndGetEmail(token);
        if (username == null) {
            // If the token is invalid, we save some time by returning early
            filterChain.doFilter(request, response);
            return;
        }

        // If the token is valid, we also want the user type
        // At this point we already know the token is valid, and do not null-check
        final String type = validateTokenAndGetUserType(token);
        logger.info("Authenticated request from " + username);

        // Register the authenticated user in the request's security context
        // This allows handlers furhter down the pipeline to access this info
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                username,
                null,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + type)));
        SecurityContextHolder.getContext().setAuthentication(auth);

        // Continue down the filter chain
        filterChain.doFilter(request, response);
    }

    /**
     * Checks whether the given JWT is valid, and extracts the email from it
     * @param token - a JWT
     * @return the email contained if the token is valid, null otherwise
     */
    public String validateTokenAndGetEmail(final String token) {
        try {
            final Algorithm hmac512 = Algorithm.HMAC512(TokenController.secret);
            final JWTVerifier verifier = JWT.require(hmac512).build();
            return verifier.verify(token).getSubject();
        } catch (final JWTVerificationException verificationEx) {
            return null;
        }
    }

    /**
     * Checks whether the given JWT is valid, and extracts the user type from it
     * @param token - a JWT
     * @return the user type contained if the token is valid, null otherwise
     */
    public String validateTokenAndGetUserType(final String token) {
        try {
            final Algorithm hmac512 = Algorithm.HMAC512(TokenController.secret);
            final JWTVerifier verifier = JWT.require(hmac512).build();
            return verifier.verify(token).getClaim("type").asString();
        } catch (final JWTVerificationException verificationEx) {
            return null;
        }
    }
}
