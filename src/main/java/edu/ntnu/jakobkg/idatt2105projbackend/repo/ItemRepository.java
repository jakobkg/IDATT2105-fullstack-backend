package edu.ntnu.jakobkg.idatt2105projbackend.repo;

import edu.ntnu.jakobkg.idatt2105projbackend.model.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Integer> {}
