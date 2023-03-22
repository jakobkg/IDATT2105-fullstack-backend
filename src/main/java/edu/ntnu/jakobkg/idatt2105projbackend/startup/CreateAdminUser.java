package edu.ntnu.jakobkg.idatt2105projbackend.startup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import edu.ntnu.jakobkg.idatt2105projbackend.model.User;
import edu.ntnu.jakobkg.idatt2105projbackend.model.User.UserType;
import edu.ntnu.jakobkg.idatt2105projbackend.repo.UserRepository;

/**
 * This class is run on applicatoin start, and creates an admin user
 * If this first user does not exist, there is no way to create further
 * admin users without manually entering the minto the database
 * which is highly undesired
 */
@Component
public class CreateAdminUser implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        User admin = new User("Admin", "Adminsen", "admin@admin.no", "admin", "Admingata 1", 1101, "Adminby");
        admin.setType(UserType.ADMIN);

        if (!userRepository.existsByEmail("admin@admin.no")) {
            userRepository.save(admin);
        }
    }
}