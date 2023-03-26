package edu.ntnu.jakobkg.idatt2105projbackend.repo;

import edu.ntnu.jakobkg.idatt2105projbackend.model.Item;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    public Iterable<Item> findByCategoryId(Integer categoryId, PageRequest pr);

    @Query("select i from Item i where i.title like '%' || :searchterm || '%' or i.description like '%' || :searchterm || '%'")
    public Iterable<Item> searchItems(@Param("searchterm") String searchterm, PageRequest page);

    public Iterable<Item> findByUserId(Integer userId, PageRequest page);
}
