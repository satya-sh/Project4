package com.example.demo;

import com.google.api.client.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;

/**
 * Spring Boot application with Spring Shell commands for managing GameRecord entities.
 */
@ShellComponent
//@SpringBootApplication
public class GameRecordApplication {
    @Autowired
    GameRecordRepository gameRecordRepository;

    /**
     * The main method that starts the Spring Boot application.
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(GameRecordApplication.class, args);
    }

    /**
     * Shell command for saving a GameRecord to Cloud Datastore.
     *
     * @param userId The user ID associated with the game record.
     * @param score  The score achieved in the game.
     * @param date   The date of the game record.
     * @return A string representation of the saved GameRecord.
     */
    @ShellMethod("Saves a gameRecord to Cloud Datastore: save-gameRecord <userid> <score> <date>")
    public String saveGameRecord(String userId, int score, String date) {
        GameRecord savedGameRecord = this.gameRecordRepository.save(new GameRecord(userId, score, date));
        return savedGameRecord.toString();
    }

    /**
     * Shell command for loading all GameRecord entities.
     *
     * @return A string representation of all GameRecord entities.
     */
    @ShellMethod("Loads all GameRecord")
    public String findAllGameRecord() {
        Iterable<GameRecord> gameRecords = this.gameRecordRepository.findAll();
        return Lists.newArrayList(gameRecords).toString();
    }

    /**
     * Shell command for loading GameRecord entities by user ID.
     *
     * @param userId The user ID to search for.
     * @return A string representation of the GameRecord entities with the given user ID.
     */
    @ShellMethod("Loads record by user: find-by-userID <userId>")
    public String findByUserId(String userId) {
        List<GameRecord> gameRecords = this.gameRecordRepository.findByUserId(userId);
        return gameRecords.toString();
    }

    /**
     * Shell command for removing all GameRecord entities.
     */
    @ShellMethod("Remove all records")
    public void removeAllGameRecords() {
        this.gameRecordRepository.deleteAll();
    }
}
