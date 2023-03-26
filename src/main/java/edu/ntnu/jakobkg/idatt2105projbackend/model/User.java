package edu.ntnu.jakobkg.idatt2105projbackend.model;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Entity
/**
 * User
 * 
 * This class represents a user, and contains relevant info
 * such as name, email address and home address
 * 
 * A user is uniquely identified by their email address, but we 
 * generate an additional unique ID number for each user. This is 
 * because a user may change their email address, so if this email address
 * was the only unique identifier we would need to update the records
 * of the user's sales listings and bookmarks when they change their email.
 * By using a separate, unique ID number, we avoid this
 */
public class User implements UserDetails {

    /**
     * There are two types of users, regular USER and ADMIN
     */
    public static enum UserType {
        USER,
        ADMIN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private UserType type;

    @Getter
    @Setter
    private String firstname;

    @Getter
    @JsonIgnore
    private String password;

    @Getter
    @Setter
    private String lastname;

    @Getter
    @Column(unique = true)
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

    public User() {
    }

    /**
     * Constructs a User object with the given data,
     * the User type USER and a randomly generated unique ID
     * 
     * @param firstname
     * @param lastname
     * @param email
     * @param password
     * @param streetAddress
     * @param postCode
     * @param city
     */
    public User(String firstname, String lastname, String email, String password, String streetAddress, Integer postCode,
            String city) throws IllegalArgumentException {

        this.type = UserType.USER;
        this.firstname = firstname;
        this.lastname = lastname;
        this.setEmail(email);
        this.setPassword(password);
        this.streetAddress = streetAddress;
        this.postCode = postCode;
        this.city = city;
    }

    // Full address getter, derived from address related fields
    public String getFullAddress() {
        return String.format("%s, %s %s", streetAddress, postCode, city);
    }

    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<SimpleGrantedAuthority> authList = new ArrayList<>();

        if (type == UserType.ADMIN) {
            authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            authList.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return authList;
    }

    @Override
    public String getUsername() {
        return email;
    }

    /**
     * We do not expire user accounts
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * We do not support locked accounts at this time
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * User credentials do not expire
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * We do not support disabling accounts at this time
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
