package edu.ntnu.jakobkg.idatt2105projbackend.repo;

import org.springframework.data.repository.CrudRepository;

import edu.ntnu.jakobkg.idatt2105projbackend.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {}
