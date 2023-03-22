package edu.ntnu.jakobkg.idatt2105projbackend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

import edu.ntnu.jakobkg.idatt2105projbackend.model.AddUserRequest;
import edu.ntnu.jakobkg.idatt2105projbackend.model.User;
import edu.ntnu.jakobkg.idatt2105projbackend.model.User.UserType;
import edu.ntnu.jakobkg.idatt2105projbackend.repo.UserRepository;


/**
 * UserController
 * 
 * This class contains all endoint handlers for the `/user` API,
 * including creation, updating, fetching and deleting of users
 */
@Controller
@RequestMapping(path = "/user")
public class UserController {
    @Autowired
    private UserRepository userRepo;

    /**
     * Add new user
     * 
     * This endpoint expects user data to be supplied as a JSON object
     * in the request body
     * 
     * Possible responses are HTTP 201 Created, or
     * HTTP 409 Conflict if another user with the same email already exists,
     * 
     * @param request
     */
    @PostMapping(path = "")
    @ResponseStatus(code = HttpStatus.CREATED)
    public @ResponseBody void addUser(
            @RequestBody AddUserRequest request) {

        // Hvis det allerede eksisterer en bruker med den angitte mailadressen,
        // svar med HTTP 409
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

    /**
     * Remove an existing user
     * 
     * This endpoint removes a user given their ID
     * This is only available if the authenticated user is
     * the same as the user being deleted, or is an admin
     * 
     * @param id - ID of the user to delete
     */
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void deleteUser(@PathVariable Integer id) {
        String authenticatedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        User loggedinUser = userRepo.findByEmail(authenticatedUsername).orElseThrow();

        if (loggedinUser.getType() == UserType.ADMIN || loggedinUser.getId() == id) {
            userRepo.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Fetch a user
     * 
     * This endpoint responds with the info of the user with the
     * given ID, or HTTP 404 if no such user exists
     * 
     * @param id - ID of the user to fetch
     * @return the requested user
     */
    @GetMapping(path = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public @ResponseBody User getUser(@PathVariable Integer id) {
        return userRepo.findById(id).orElseThrow(() -> {
            return new ResponseStatusException(HttpStatus.NOT_FOUND);
        });
    }

    /**
     * Update an existing user
     * 
     * This endpoint makes changes to an existing user given their ID
     * and the updated values of the fields to update.
     * 
     * This endpoint requires the authenticated user to be the user
     * to update, or an admin
     * 
     * @param id - the ID of the user to change
     * @param request - the updated values (null fields are ignored)
     */
    @PutMapping(path = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public @ResponseBody void updateUser(@PathVariable Integer id,
            @RequestBody AddUserRequest request) {

        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (authenticatedUser.getId() != id || authenticatedUser.getType() != UserType.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

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
    }

    /**
     * Get a list of all registered users
     * 
     * This endpoint responds with a paginated list of
     * all registered users. The default page size is
     * 24 users, but this can be changed using the `perPage`
     * request parameter
     * 
     * @param page - the desired page number (1-indexed)
     * @param perPage - the desired number of entries per page
     * @return - a list of users
     */
    @GetMapping(path = "")
    public @ResponseBody List<User> getAllUsers(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "24") Integer perPage) {
        // Page 0 and lower are empty
        if (page > 1) {
            return new ArrayList<>();
        }

        // The built-in PageRequests are 0-indexed, so we do -1 here to translate
        // our 1-indexed requests
        return userRepo.findAll(PageRequest.of(page - 1, perPage)).toList();
    }

    /**
     * Change the type of a user
     * 
     * This admin-only endpoint allows admins to grant or revoke
     * adminsitrator status to other users
     * 
     * @param id - the user whose admin status to change
     * @param status - `true` to make the given user an admin, `false` to revoke admin status
     */
    @PutMapping(path = "admin/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void setAdmin(@PathVariable Integer id, @RequestBody Boolean status) {
        User user = userRepo.findById(id).orElseThrow(() -> {
            return new ResponseStatusException(HttpStatus.NOT_FOUND);
        });

        if (status) {
            user.setType(UserType.ADMIN);
        } else {
            user.setType(UserType.USER);
        }

        userRepo.save(user);
    }
}
