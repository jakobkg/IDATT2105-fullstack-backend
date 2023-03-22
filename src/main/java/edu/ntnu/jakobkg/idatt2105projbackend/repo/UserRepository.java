package edu.ntnu.jakobkg.idatt2105projbackend.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ntnu.jakobkg.idatt2105projbackend.model.User;

/**
 * UserRepository
 * 
 * This interface represents the connection between our MySQL database and 
 * managed Spring objects.
 * 
 * JpaRepository already supports search by ID, we extend this interface
 * to also support searching by email address
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
