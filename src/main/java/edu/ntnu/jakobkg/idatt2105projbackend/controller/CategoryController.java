package edu.ntnu.jakobkg.idatt2105projbackend.controller;
import edu.ntnu.jakobkg.idatt2105projbackend.model.Category;
import edu.ntnu.jakobkg.idatt2105projbackend.repo.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryRepository categoryRepository;

    Logger logger = LoggerFactory.getLogger(CategoryController.class);

    CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @PostMapping("")
    Category createNewCategory( @RequestBody String categoryName){
        return categoryRepository.save(new Category(categoryName));
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
        Category result = categoryRepository.findById(id).orElseThrow(()->{
            logger.warn("Could not find category with id " + id);
            return new ResponseStatusException(HttpStatus.NOT_FOUND);
        });
        return result;
    }

    @DeleteMapping("/{id}")
    void deleteCategory(@PathVariable Integer id){
        if(categoryRepository.existsById(id)){
            categoryRepository.deleteById(id);
        }else{
            logger.warn("Category "+ id + " does not exist");
        }

    }
}
