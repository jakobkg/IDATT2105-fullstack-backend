package edu.ntnu.jakobkg.idatt2105projbackend.helper;

import com.auth0.jwt.JWT;

public class TokenHelper {
    public static String extractEmail(String token) {
        return JWT.decode(token.substring(7)).getSubject();
    }
}
