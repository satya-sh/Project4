package com.example.demo;

import com.google.api.client.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

/**
 * Spring Boot application with Spring Shell commands for managing User entities.
 */
@ShellComponent
//@SpringBootApplication
public class UserApplication {

    @Autowired
    UserRepository userRepository;

    /**
     * The main method that starts the Spring Boot application.
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

    /**
     * Shell command for saving a user to Cloud Datastore.
     *
     * @param userId  The user ID.
     * @param handle  The user's handle or username.
     * @param email   The user's email address.
     * @param date    The date associated with the user.
     * @return A string representation of the saved User.
     */
    @ShellMethod("Saves a user to Cloud Datastore: save-user <userid> <score> <date>")
    public String saveUser(String userId, String handle, String email, String date) {
        User savedUser = this.userRepository.save(new User(userId, handle, email, date));
        return savedUser.toString();
    }

    /**
     * Shell command for loading all User entities.
     *
     * @return A string representation of all User entities.
     */
    @ShellMethod("Loads all User")
    public String findAllUser() {
        Iterable<User> user = this.userRepository.findAll();
        return Lists.newArrayList(user).toString();
    }

    /**
     * Shell command for removing all User entities.
     */
    @ShellMethod("Remove all users")
    public void removeAllUser() {
        this.userRepository.deleteAll();
    }
}
