package edu.ntnu.jakobkg.idatt2105projbackend.builders;

import edu.ntnu.jakobkg.idatt2105projbackend.model.User;

public class UserBuilder {
    private User user;

    public UserBuilder() {
        user = new User();
    }

    public User build() {
        return user;
    }

    public UserBuilder firstName(String firstName) {
        user.setFirstname(firstName);
        return this;
    }

    public UserBuilder lastName(String lastName) {
        user.setLastname(lastName);
        return this;
    }

    public UserBuilder email(String email) {
        user.setEmail(email);
        return this;
    }

    public UserBuilder streetAddress(String streetAddress) {
        user.setStreetAddress(streetAddress);
        return this;
    }

    public UserBuilder postCode(Integer postCode) {
        user.setPostCode(postCode);
        return this;
    }

    public UserBuilder city(String city) {
        user.setCity(city);
        return this;
    }
}
