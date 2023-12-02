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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for managing user-related operations.
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    /**
     * Retrieves a user by their Google ID.
     *
     * @param googleId The Google ID of the user.
     * @return The user information if found, or 404 if not found.
     */
    @GetMapping("/{googleId}")
    public ResponseEntity<User> getUserById(@PathVariable String googleId) {
        User user = userRepository.findById(googleId).orElse(null);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Checks if a user exists in the user table by their Google ID.
     *
     * @param googleId The Google ID of the user.
     * @return A message indicating whether the user exists or not.
     */
    @GetMapping("/exists/{googleId}")
    public ResponseEntity<String> checkUserExists(@PathVariable String googleId) {
        if (userRepository.existsById(googleId)) {
            return new ResponseEntity<>("User exists in the user table", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User does not exist in the user table", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Updates the user handle (name) for a given user.
     *
     * @param googleId   The Google ID of the user.
     * @param newHandle  The new user handle to set.
     * @return The updated user information if successful, or 404 if the user is not found.
     */
    @PatchMapping("/{googleId}/update-handle")
    public ResponseEntity<User> updateHandle(
            @PathVariable String googleId,
            @RequestBody String newHandle) {

        User user = userRepository.findById(googleId).orElse(null);
        if (user != null) {
            user.setUserHandle(newHandle);
            userRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a user by their Google ID.
     *
     * @param googleId The Google ID of the user to delete.
     * @return A message indicating the success of the operation.
     */
    @DeleteMapping("/{googleId}")
    public ResponseEntity<String> deleteUser(@PathVariable String googleId) {
        userRepository.deleteById(googleId);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }

    // Add other UserController methods as needed
}
