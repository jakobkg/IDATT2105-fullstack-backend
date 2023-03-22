package edu.ntnu.jakobkg.idatt2105projbackend.model;

public record AddUserRequest(
    String email,
    String password,
    String firstname,
    String lastname,
    String streetAddress,
    Integer postCode,
    String city) {
}
