package edu.ntnu.jakobkg.idatt2105projbackend.model;

/**
 * LoginRequest
 * 
 * This record class represents a login attempt,
 * with the email and password of the user trying to log in
 */
public record LoginRequest(String email, String password) {}
