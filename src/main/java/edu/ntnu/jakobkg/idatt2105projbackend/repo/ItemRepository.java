package edu.ntnu.jakobkg.idatt2105projbackend.repo;

import edu.ntnu.jakobkg.idatt2105projbackend.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {}
