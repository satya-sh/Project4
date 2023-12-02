package com.example.demo;

import java.util.List;

import com.google.common.collect.Lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@SpringBootApplication
public class DemoApplication {
    // Autowire your repositories or services if needed
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRecordRepository gameRecordRepository;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    /**
     * Saves a user to the user table.
     *
     * @param googleId   The Google ID of the user.
     * @param userHandle The user handle to set.
     * @return The saved user information.
     */
    @ShellMethod("Saves a user to the user table: save-user <googleId> <userHandle>")
    public String saveUser(String googleId, String userHandle) {
        User savedUser = userRepository.save(new User(googleId, userHandle));
        return savedUser.toString();
    }

    /**
     * Retrieves all users from the user table.
     *
     * @return The list of all users.
     */
    @ShellMethod("Retrieves all users from the user table")
    public String findAllUsers() {
        Iterable<User> users = userRepository.findAll();
        List<User> userList = Lists.newArrayList(users);
        return userList.toString();
    }

    /**
     * Retrieves users by user handle from the user table.
     *
     * @param userHandle The user handle to search for.
     * @return The list of users with the specified handle.
     */
    @ShellMethod("Retrieves users by user handle: find-by-user-handle <userHandle>")
    public String findByUserHandle(String userHandle) {
        List<User> users = userRepository.findByUserHandle(userHandle);
        return users.toString();
    }

    // Similar methods for game records can be added based on your requirements

    /**
     * Removes all users and game records from the tables.
     */
    @ShellMethod("Removes all users and game records")
    public void removeAll() {
        userRepository.deleteAll();
        gameRecordRepository.deleteAll();
    }
}