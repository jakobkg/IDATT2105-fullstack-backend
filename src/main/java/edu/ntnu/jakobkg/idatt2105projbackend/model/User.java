package edu.ntnu.jakobkg.idatt2105projbackend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {

    public static enum UserType {
        USER,
        ADMIN
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private UserType type;

    private String firstname;
    private String lastname;

    private String email;

    private String streetAddress;

    private Integer postCode;

    private String city;

    public User() {}

    public User(UserType type, String firstname, String lastname, String email, String streetAddress, Integer postCode, String city) {
        this.type = type;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.streetAddress = streetAddress;
        this.postCode = postCode;
        this.city = city;
    }

    public Integer getID() {
        return id;
    }

    // Access type getter and setter
    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    // Full address getter, derived from address related fields
    public String getFullAddress() {
        return String.format("%s, %s %s", streetAddress, postCode, city);
    }

    // First name getter and setter
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    // Surname getter and setter
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    // Email address getter and setter
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Street address getter and setter
    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    // Post code getter and setter
    public Integer getPostCode() {
        return postCode;
    }

    public void setPostCode(Integer postCode) {
        this.postCode = postCode;
    }

    // City getter and setter
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}