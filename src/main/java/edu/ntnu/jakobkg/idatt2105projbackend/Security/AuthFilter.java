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

@Controller
public class AuthFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LogManager.getLogger(AuthFilter.class);

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // check Bearer auth header
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);
        final String username = validateTokenAndGetUserId(token);
        if (username == null) {
            filterChain.doFilter(request, response);
            return;
        }

        final String type = validateTokenAndGetUserType(token);


        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                username,
                null,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + type)));
        SecurityContextHolder.getContext().setAuthentication(auth);

        // then, continue with authenticated user context
        filterChain.doFilter(request, response);
    }

    public String validateTokenAndGetUserId(final String token) {
        try {
            final Algorithm hmac512 = Algorithm.HMAC512(TokenController.secret);
            final JWTVerifier verifier = JWT.require(hmac512).build();
            return verifier.verify(token).getSubject();
        } catch (final JWTVerificationException verificationEx) {
            LOGGER.warn("token is invalid: {}", verificationEx.getMessage());
            return null;
        }
    }

    public String validateTokenAndGetUserType(final String token) {
        try {
            final Algorithm hmac512 = Algorithm.HMAC512(TokenController.secret);
            final JWTVerifier verifier = JWT.require(hmac512).build();
            return verifier.verify(token).getClaim("type").asString();
        } catch (final JWTVerificationException verificationEx) {
            LOGGER.warn("token is invalid: {}", verificationEx.getMessage());
            return null;
        }
    }
}
