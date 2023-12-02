package com.example.demo;

import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;

/**
 * Entity representing a user in the application.
 */
@Entity(name = "users")
public class User {
    @Id
    private String googleId; // Using Google ID as the unique identifier

    private String userHandle;

    public User() {
        // Empty constructor needed for Datastore
    }

    // Constructor with parameters
    public User(String googleId, String userHandle) {
        this.googleId = googleId;
        this.userHandle = userHandle;
    }
    // Getters and Setters

    /**
     * Gets the Google ID of the user.
     *
     * @return The Google ID.
     */
    public String getGoogleId() {
        return googleId;
    }

    /**
     * Sets the Google ID of the user.
     *
     * @param googleId The Google ID to set.
     */
    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    /**
     * Gets the user handle.
     *
     * @return The user handle.
     */
    public String getUserHandle() {
        return userHandle;
    }

    /**
     * Sets the user handle.
     *
     * @param userHandle The user handle to set.
     */
    public void setUserHandle(String userHandle) {
        this.userHandle = userHandle;
    }
}
