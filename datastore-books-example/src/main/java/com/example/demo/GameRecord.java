package com.example.demo;

import org.springframework.data.annotation.Id;
import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import java.time.LocalDateTime;

/**
 * Entity representing a game record in the application.
 */
@Entity(name = "game_records")
public class GameRecord {
    @Id
    private Long gameId;

    private User user;

    private int gameScore;

    private LocalDateTime createdAt;

    public GameRecord() {
        // Empty constructor needed for Datastore
    }

    // Constructor with parameters
    public GameRecord(Long gameId, User user, int gameScore, LocalDateTime createdAt) {
        this.gameId = gameId;
        this.user = user;
        this.gameScore = gameScore;
        this.createdAt = createdAt;
    }
    // Getters and Setters

    /**
     * Gets the game ID.
     *
     * @return The game ID.
     */
    public Long getGameId() {
        return gameId;
    }

    /**
     * Sets the game ID.
     *
     * @param gameId The game ID to set.
     */
    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    /**
     * Gets the associated user.
     *
     * @return The associated user.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the associated user.
     *
     * @param user The user to set.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the game score.
     *
     * @return The game score.
     */
    public int getGameScore() {
        return gameScore;
    }

    /**
     * Sets the game score.
     *
     * @param gameScore The game score to set.
     */
    public void setGameScore(int gameScore) {
        this.gameScore = gameScore;
    }

    /**
     * Gets the creation timestamp.
     *
     * @return The creation timestamp.
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the creation timestamp.
     *
     * @param createdAt The creation timestamp to set.
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
