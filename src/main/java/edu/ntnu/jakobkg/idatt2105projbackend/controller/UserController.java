package edu.ntnu.jakobkg.idatt2105projbackend.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import edu.ntnu.jakobkg.idatt2105projbackend.helper.TokenHelper;
import edu.ntnu.jakobkg.idatt2105projbackend.model.AddUserRequest;
import edu.ntnu.jakobkg.idatt2105projbackend.model.User;
import edu.ntnu.jakobkg.idatt2105projbackend.model.User.UserType;
import edu.ntnu.jakobkg.idatt2105projbackend.repo.UserRepository;

@Controller
@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    private UserRepository userRepo;

    @PostMapping(path = "")
    @ResponseStatus(code = HttpStatus.CREATED)
    public @ResponseBody void addUser(
            @RequestBody AddUserRequest request) {

        if (userRepo.existsByEmail(request.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        User newUser = new User(
                request.firstname(),
                request.lastname(),
                request.email(),
                request.password(),
                request.streetAddress(),
                request.postCode(),
                request.city());

        userRepo.save(newUser);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void deleteUser(@RequestHeader("Authorization") String token, @PathVariable Integer id) {
        String authenticatedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedinUser = userRepo.findByEmail(authenticatedUsername).orElseThrow();

        if (loggedinUser.getType() == UserType.ADMIN || loggedinUser.getId() == id) {
            userRepo.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public @ResponseBody User getUser(@PathVariable Integer id) {
        return userRepo.findById(id).orElseThrow();
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public @ResponseBody String updateUser(@PathVariable Integer id,
            @RequestBody AddUserRequest request) {
    
        User user = userRepo.findById(id).orElseThrow(() -> {
            return new ResponseStatusException(HttpStatus.NOT_FOUND);
        });

        if (userRepo.existsByEmail(request.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        if (Objects.nonNull(request.firstname())) {
            user.setFirstname(request.firstname());
        }

        if (Objects.nonNull(request.lastname())) {
            user.setLastname(request.lastname());
        }

        if (Objects.nonNull(request.email())) {
            user.setEmail(request.email());
        }

        if (Objects.nonNull(request.password())) {
            user.setPassword(request.password());
        }

        if (Objects.nonNull(request.streetAddress())) {
            user.setStreetAddress(request.streetAddress());
        }

        if (Objects.nonNull(request.postCode())) {
            user.setPostCode(request.postCode());
        }

        if (Objects.nonNull(request.city())) {
            user.setCity(request.city());
        }

        userRepo.save(user);
        return "OK";
    }

    @GetMapping(path = "")
    public @ResponseBody List<User> getAllUsers(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "24") Integer perPage) {
        return userRepo.findAll(PageRequest.of(page, perPage)).toList();
    }
}
