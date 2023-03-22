package edu.ntnu.jakobkg.idatt2105projbackend.model;

/**
 * AddUserRequest
 * 
 * This class defines the fields required to create
 * a new user, or update an existing one
 * 
 * Note that user ID and type (USER/ADMIN) is not present, as ID
 * is immutable and admin status can only be edited through privileged endpoints
 */
public record AddUserRequest(
    String email,
    String password,
    String firstname,
    String lastname,
    String streetAddress,
    Integer postCode,
    String city) {}
