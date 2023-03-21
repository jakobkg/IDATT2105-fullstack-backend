package edu.ntnu.jakobkg.idatt2105projbackend.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import edu.ntnu.jakobkg.idatt2105projbackend.model.AddUserRequest;
import edu.ntnu.jakobkg.idatt2105projbackend.model.UserModel;
import edu.ntnu.jakobkg.idatt2105projbackend.repo.UserRepository;

@Controller
@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    private UserRepository userRepo;

    @PostMapping(path = "")
    @ResponseStatus(code = HttpStatus.CREATED)
    public @ResponseBody String addUser(
            @RequestBody AddUserRequest request) {

        UserModel newUser = new UserModel(
                request.firstname(),
                request.lastname(),
                request.email(),
                request.password(),
                request.streetAddress(),
                request.postCode(),
                request.city());

        userRepo.save(newUser);
        return "OK";
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public @ResponseBody UserModel getUser(@PathVariable Integer id) {
        return userRepo.findById(id).orElseThrow();
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public @ResponseBody String updateUser(@PathVariable Integer id,
            @RequestParam(required = false) String firstname,
            @RequestParam(required = false) String lastname,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) String streetAddress,
            @RequestParam(required = false) Integer postCode,
            @RequestParam(required = false) String city) {
    
        UserModel user = userRepo.findById(id).orElseThrow(() -> {
            return new ResponseStatusException(HttpStatus.NOT_FOUND);
        });

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

    @GetMapping(path = "")
    public @ResponseBody List<UserModel> getAllUsers(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "24") Integer perPage) {
        return userRepo.findAll(PageRequest.of(page, perPage)).toList();
    }
}
