package com.example.demo;

import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import org.springframework.data.annotation.Id;

/**
 * Represents a game record entity with information such as user ID, score, and date.
 */
@Entity(name = "games")
public class GameRecord {
    /**
     * Unique identifier for the game record.
     */
    @Id
    Long id;

    /**
     * Score achieved in the game.
     */
    int score;

    /**
     * Date of the game record.
     */
    String date;

    /**
     * User ID associated with the game record.
     */
    String userId;

    /**
     * Constructs a new GameRecord with the specified parameters.
     *
     * @param userId The user ID associated with the game record.
     * @param score  The score achieved in the game.
     * @param date   The date of the game record.
     */
    public GameRecord(String userId, int score, String date) {
        this.userId = userId;
        this.score = score;
        this.date = date;
    }

    /**
     * Gets the unique identifier for the game record.
     *
     * @return The game record's unique identifier.
     */
    public long getId() {
        return this.id;
    }

    /**
     * Sets the unique identifier for the game record.
     *
     * @param id The new unique identifier for the game record.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the user ID associated with the game record.
     *
     * @return The user ID.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user ID for the game record.
     *
     * @param userId The new user ID.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Gets the score achieved in the game.
     *
     * @return The score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the score for the game record.
     *
     * @param score The new score.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Gets the date of the game record.
     *
     * @return The date.
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date for the game record.
     *
     * @param date The new date.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Returns a string representation of the GameRecord object.
     *
     * @return A string representation of the GameRecord.
     */
    @Override
    public String toString() {
        return "GameRecord{" +
                "id=" + id +
                ", score=" + score +
                ", date='" + date + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
