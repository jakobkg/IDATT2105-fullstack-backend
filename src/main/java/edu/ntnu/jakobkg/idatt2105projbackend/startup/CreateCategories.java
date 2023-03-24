package edu.ntnu.jakobkg.idatt2105projbackend.startup;

import edu.ntnu.jakobkg.idatt2105projbackend.model.Category;
import edu.ntnu.jakobkg.idatt2105projbackend.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * This class is run on application start and creates a set of categories
 */
@Component
public class CreateCategories implements CommandLineRunner {
    @Autowired
    CategoryRepository categoryRepo;

    @Override
    public void run(String... args) {
        if (categoryRepo.count() == 0) {
            categoryRepo.save(new Category("Elektronikk"));
            categoryRepo.save(new Category("Klær"));
            categoryRepo.save(new Category("Kunst"));
            categoryRepo.save(new Category("Dyr"));
            categoryRepo.save(new Category("Møbler"));
            categoryRepo.save(new Category("Sport"));
            categoryRepo.save(new Category("Planter"));
            categoryRepo.save(new Category("Hvitevarer"));
        }
    }
}