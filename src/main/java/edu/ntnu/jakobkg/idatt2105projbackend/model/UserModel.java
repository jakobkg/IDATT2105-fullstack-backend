package edu.ntnu.jakobkg.idatt2105projbackend.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
public class UserModel {

    public static enum UserType {
        USER,
        ADMIN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Integer id;

    @Getter
    @Setter
    private UserType type;

    @Getter
    @Setter
    private String firstname;

    @Getter
    @Setter
    @JsonIgnore
    private String password;

    @Getter
    @Setter
    private String lastname;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String streetAddress;

    @Getter
    @Setter
    private Integer postCode;

    @Getter
    @Setter
    private String city;

    public UserModel() {
    }

    public UserModel(String firstname, String lastname, String email, String password, String streetAddress, Integer postCode,
            String city) {
        this.type = UserType.USER;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = new BCryptPasswordEncoder().encode(password);
        this.streetAddress = streetAddress;
        this.postCode = postCode;
        this.city = city;

    }

    // Full address getter, derived from address related fields
    public String getFullAddress() {
        return String.format("%s, %s %s", streetAddress, postCode, city);
    }
}
