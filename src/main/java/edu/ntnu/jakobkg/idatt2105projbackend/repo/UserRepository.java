package edu.ntnu.jakobkg.idatt2105projbackend.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.ntnu.jakobkg.idatt2105projbackend.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Integer> {
    Optional<UserModel> findByEmail(String email);
}
