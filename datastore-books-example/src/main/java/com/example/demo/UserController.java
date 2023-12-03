package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpSession;

/**
 * Controller class for managing User entities through RESTful endpoints.
 */
@RestController
public class UserController {

    private final UserRepository userRepository;

    /**
     * Constructs a new UserController with the specified UserRepository.
     *
     * @param userRepository The repository for User entities.
     */
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Endpoint for saving a User entity.
     *
     * @param user The User entity to be saved.
     * @return A message indicating the success or failure of the operation.
     */
    @PostMapping("/saveUser")
    @CrossOrigin(origins = "*")
    public String saveUser(@RequestBody User user) {
        if (user == null) {
            return "The user is invalid";
        }
        this.userRepository.save(user);
        return "success";
    }

    /**
     * Endpoint for retrieving all User entities.
     *
     * @return A list of all User entities stored in the repository.
     */
    @GetMapping("/findAllUser")
    @ResponseBody
    @CrossOrigin(origins = "*")
    public List<User> findAllUser() {
        Iterable<User> user = this.userRepository.findAll();
        List<User> userList = new ArrayList<>();
        user.forEach(userList::add);
        return userList;
    }
}
