package edu.ntnu.jakobkg.idatt2105projbackend.controller;

import edu.ntnu.jakobkg.idatt2105projbackend.model.Category;
import edu.ntnu.jakobkg.idatt2105projbackend.repo.CategoryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

public class CategoryController {

    private final CategoryRepository categoryRepository;

    CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @PostMapping("/category")
    Category createNewCategory( Category newCategory){
        return categoryRepository.save(newCategory);
    }

    @GetMapping("/category")
    Iterable<Category> all() {
        return categoryRepository.findAll();
    }

    @GetMapping("/category/{id}")
    Optional<Category> getCategory(@PathVariable Integer id){
        return categoryRepository.findById(id);
    }

    @DeleteMapping("/category/{id}")
    void deleteCategory(@PathVariable Integer id){
        categoryRepository.deleteById(id);
    }
}
