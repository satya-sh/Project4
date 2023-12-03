package com.example.demo;

import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;

/**
 * Represents a user entity with information such as user ID, handle, email, score, and date.
 */
@Entity(name = "users")
public class User {

    /**
     * Unique identifier for the user.
     */
    @Id
    Long id;

    /**
     * Email address of the user.
     */
    String email;

    /**
     * User's chosen handle or username.
     */
    String handle;

    /**
     * Date associated with the user, typically registration date.
     */
    String date;

    /**
     * User ID associated with the user, which could be different from the username.
     */
    String userId;

    /**
     * Constructs a new User with the specified parameters.
     *
     * @param userId The unique identifier for the user.
     * @param handle The user's chosen handle or username.
     * @param email  The email address of the user.
     * @param date   The date associated with the user, typically the registration date.
     */
    public User(String userId, String handle, String email, String date) {
        this.handle = handle;
        this.userId = userId;
        this.email = email;
        this.date = date;
    }

    /**
     * Gets the unique identifier for the user.
     *
     * @return The user's unique identifier.
     */
    public long getId() {
        return this.id;
    }

    /**
     * Sets the unique identifier for the user.
     *
     * @param id The new unique identifier for the user.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the user ID associated with the user.
     *
     * @return The user ID.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user ID for the user.
     *
     * @param userId The new user ID.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Gets the email address of the user.
     *
     * @return The user's email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address for the user.
     *
     * @param email The new email address.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the date associated with the user.
     *
     * @return The date, typically the registration date.
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date for the user, typically the registration date.
     *
     * @param date The new date.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gets the user's chosen handle or username.
     *
     * @return The user's handle.
     */
    public String getHandle() {
        return handle;
    }

    /**
     * Sets the user's handle or username.
     *
     * @param handle The new handle or username.
     */
    public void setHandle(String handle) {
        this.handle = handle;
    }

    /**
     * Returns a string representation of the User object.
     *
     * @return A string representation of the User.
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", score=" + email +
                ", handle='" + handle + '\'' +
                ", date='" + date + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
