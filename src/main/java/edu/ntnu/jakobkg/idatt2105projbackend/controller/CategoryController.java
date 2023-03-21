package edu.ntnu.jakobkg.idatt2105projbackend.controller;
import edu.ntnu.jakobkg.idatt2105projbackend.model.Category;
import edu.ntnu.jakobkg.idatt2105projbackend.repo.CategoryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;

@RequestMapping("/category")
public class CategoryController {
    private final CategoryRepository categoryRepository;

    CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @PostMapping("")
    Category createNewCategory( Category newCategory){
        return categoryRepository.save(newCategory);
    }

    @GetMapping("")
    Iterable<Category> all() {
        return categoryRepository.findAll();
    }

    @PutMapping("/{id}")
    Optional<Category> updateCategoryName(@RequestBody Category updatedCategory,@PathVariable Integer id){
        return categoryRepository.findById(id).map(
                category-> {
                    category.setCategoryName(updatedCategory.getCategoryName());
                    return categoryRepository.save(category);
                });
        }

    @GetMapping("/{id}")
    Category getCategory(@PathVariable Integer id){
        Optional<Category> result = categoryRepository.findById(id);
        return result.orElseThrow(() -> new NoSuchElementException(String.valueOf(id)));
    }

    @DeleteMapping("/{id}")
    void deleteCategory(@PathVariable Integer id){
        categoryRepository.deleteById(id);
    }
}
