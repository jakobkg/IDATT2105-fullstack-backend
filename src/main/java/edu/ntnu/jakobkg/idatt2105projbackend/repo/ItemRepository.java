package edu.ntnu.jakobkg.idatt2105projbackend.repo;

import edu.ntnu.jakobkg.idatt2105projbackend.model.User;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<User, Integer> {}
