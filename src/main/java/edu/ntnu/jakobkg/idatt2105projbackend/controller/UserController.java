package edu.ntnu.jakobkg.idatt2105projbackend.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import edu.ntnu.jakobkg.idatt2105projbackend.model.User;
import edu.ntnu.jakobkg.idatt2105projbackend.model.User.UserType;
import edu.ntnu.jakobkg.idatt2105projbackend.repo.UserRepository;

@Controller
@RequestMapping(path = "/users")
public class UserController {
    @Autowired
    private UserRepository userRepo;

    @PostMapping(path = "/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public @ResponseBody String addUser(
        @RequestParam String firstname, 
        @RequestParam String lastname,
        @RequestParam String email, 
        @RequestParam String streetAddress,
        @RequestParam Integer postCode,
        @RequestParam String city) {
            
        User newUser = new User(
            UserType.USER,
            firstname,
            lastname,
            email,
            streetAddress,
            postCode,
            city
        );

        userRepo.save(newUser);
        return "OK";
    }

    /**
     * @param id
     * @param firstname
     * @param lastname
     * @param email
     * @param streetAddress
     * @param postCode
     * @param city
     * @return
     */
    @PostMapping(path = "/update/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public @ResponseBody String updateUser(@PathVariable Integer id,
        @RequestParam(required = false) String firstname,
        @RequestParam(required = false) String lastname,
        @RequestParam(required = false) String email,
        @RequestParam(required = false) String streetAddress,
        @RequestParam(required = false) Integer postCode,
        @RequestParam(required = false) String city
    ) {
        User user = userRepo.findById(id).orElseThrow();

        if (Objects.nonNull(firstname)) {
            user.setFirstname(firstname);
        }

        if (Objects.nonNull(lastname)) {
            user.setLastname(lastname);
        }

        if (Objects.nonNull(email)) {
            user.setEmail(email);
        }

        if (Objects.nonNull(streetAddress)) {
            user.setStreetAddress(streetAddress);
        }

        if (Objects.nonNull(postCode)) {
            user.setPostCode(postCode);
        }

        if (Objects.nonNull(city)) {
            user.setCity(city);
        }

        userRepo.save(user);
        return "OK";
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepo.findAll();
    }
}
