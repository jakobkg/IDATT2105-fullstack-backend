package edu.ntnu.jakobkg.idatt2105projbackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.ntnu.jakobkg.idatt2105projbackend.builders.UserBuilder;
import edu.ntnu.jakobkg.idatt2105projbackend.model.User;
import edu.ntnu.jakobkg.idatt2105projbackend.repo.UserRepository;

@Controller
@RequestMapping(path = "/users")
public class UserController {
    @Autowired
    private UserRepository userRepo;

    @PostMapping(path = "/add")
    public @ResponseBody String addUser(
        @RequestParam String first_name, 
        @RequestParam String last_name,
        @RequestParam String email, 
        @RequestParam String street_address) {
        UserBuilder userBuilder = new UserBuilder();

        userBuilder = new UserBuilder()
            .firstName(first_name)
            .lastName(last_name)
            .email(email)
            .streetAddress(street_address);

        userRepo.save(userBuilder.build());
        return "OK";
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepo.findAll();
    }
}
