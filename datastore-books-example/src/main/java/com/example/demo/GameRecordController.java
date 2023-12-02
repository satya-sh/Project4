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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * Controller class for managing game record-related operations.
 */
@RestController
@RequestMapping("/game-records")
public class GameRecordController {
    @Autowired
    private GameRecordRepository gameRecordRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Retrieves previous game scores of a user with pagination and filtering.
     *
     * @param googleId The Google ID of the user.
     * @param page     The page number.
     * @param size     The page size.
     * @param sort     The sorting criteria.
     * @param minScore The minimum game score threshold.
     * @return The list of game records based on the specified criteria.
     */
    @GetMapping("/user/{googleId}/previous-scores")
    public ResponseEntity<List<GameRecord>> getUserPreviousScores(
            @PathVariable String googleId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt,desc") String[] sort,
            @RequestParam(defaultValue = "0") int minScore) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        List<GameRecord> previousScores = gameRecordRepository.findByUserGoogleId(googleId, pageable);

        // Filter the records based on a minimum score threshold
        List<GameRecord> filteredScores = previousScores.stream()
                .filter(record -> record.getGameScore() > minScore)
                .collect(Collectors.toList());

        return new ResponseEntity<>(filteredScores, HttpStatus.OK);
    }

    /**
     * Retrieves the top game records with pagination and sorting.
     *
     * @param page The page number.
     * @param size The page size.
     * @param sort The sorting criteria.
     * @return The list of top game records based on the specified criteria.
     */
    @GetMapping("/top")
    public ResponseEntity<List<GameRecord>> getTopGameRecords(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "gameScore,desc") String[] sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        List<GameRecord> topGameRecords = gameRecordRepository.findTopByOrderByGameScoreDesc(pageable);
        return new ResponseEntity<>(topGameRecords, HttpStatus.OK);
    }

    /**
     * Retrieves a specific game record by its ID.
     *
     * @param gameId The ID of the game record.
     * @return The game record if found, or 404 if not found.
     */
    @GetMapping("/{gameId}")
    public ResponseEntity<GameRecord> getGameRecordById(@PathVariable Long gameId) {
        GameRecord gameRecord = gameRecordRepository.findById(gameId).orElse(null);
        if (gameRecord != null) {
            return new ResponseEntity<>(gameRecord, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Saves a new game record.
     *
     * @param gameRecord The game record to save.
     * @return A message indicating the success of the operation.
     */
    @PostMapping
    public ResponseEntity<String> saveGameRecord(@RequestBody GameRecord gameRecord) {
        gameRecord.setCreatedAt(LocalDateTime.now()); // Set the creation timestamp
        gameRecordRepository.save(gameRecord);
        return new ResponseEntity<>("Game Record saved successfully", HttpStatus.CREATED);
    }

    /**
     * Deletes a game record by its ID.
     *
     * @param gameId The ID of the game record to delete.
     * @return A message indicating the success of the operation.
     */
    @DeleteMapping("/{gameId}")
    public ResponseEntity<String> deleteGameRecord(@PathVariable Long gameId) {
        gameRecordRepository.deleteById(gameId);
        return new ResponseEntity<>("Game Record deleted successfully", HttpStatus.OK);
    }

    // Add other GameRecordController methods as needed
}