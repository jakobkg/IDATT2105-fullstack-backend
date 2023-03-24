package edu.ntnu.jakobkg.idatt2105projbackend.startup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import edu.ntnu.jakobkg.idatt2105projbackend.model.User;
import edu.ntnu.jakobkg.idatt2105projbackend.repo.UserRepository;

/**
 * This class is run on applications start and creates a set of users
 */
@Component
public class CreateUsers implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;

    @Override
    public void run(String... args) {

        if (!userRepository.existsByEmail("ole@nordmann.no")) {
            userRepository.save(new User("Ole", "Nordmann", "ole@nordmann.no", "ole123", "Elgesetergate 2", 7031, "Trondheim"));
        }

        if (!userRepository.existsByEmail("kari@olsen.no")) {
            userRepository.save(new User("Kari", "Olsen", "kari@olsen.no", "kari123", "Svalevegen 6", 7045, "Trondheim"));
        }

    }
}