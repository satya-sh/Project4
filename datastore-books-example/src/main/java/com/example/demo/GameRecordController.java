package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * Controller class for managing GameRecord entities through RESTful endpoints.
 */
@RestController
public class GameRecordController {

    private final GameRecordRepository gameRecordRepository;

    /**
     * Constructs a new GameRecordController with the specified GameRecordRepository.
     *
     * @param gameRecordRepository The repository for GameRecord entities.
     */
    public GameRecordController(GameRecordRepository gameRecordRepository) {
        this.gameRecordRepository = gameRecordRepository;
    }

    /**
     * Endpoint for saving a GameRecord entity.
     *
     * @param gameRecord The GameRecord entity to be saved.
     * @return A message indicating the success or failure of the operation.
     */
    @PostMapping("/saveGameRecord")
    @CrossOrigin(origins = "*")
    public String saveGameRecord(@RequestBody GameRecord gameRecord) {
        if (gameRecord == null) {
            return "The game record is invalid";
        }
        this.gameRecordRepository.save(gameRecord);
        return "success";
    }

    /**
     * Endpoint for retrieving all GameRecord entities.
     *
     * @return A list of all GameRecord entities stored in the repository.
     */
    @GetMapping("/findAllGameRecord")
    @ResponseBody
    @CrossOrigin(origins = "*")
    public List<GameRecord> findAllGameRecord() {
        Iterable<GameRecord> gameRecords = this.gameRecordRepository.findAll();
        List<GameRecord> gameRecordList = new ArrayList<>();
        gameRecords.forEach(gameRecordList::add);
        return gameRecordList;
    }

    /**
     * Endpoint for retrieving GameRecord entities by user ID.
     *
     * @param userId The user ID to filter GameRecord entities.
     * @return A list of GameRecord entities with the given user ID.
     */
    @GetMapping("/findByUserId")
    @ResponseBody
    @CrossOrigin(origins = "*")
    public List<GameRecord> findByUserId(@RequestParam String userId) {
        Iterable<GameRecord> gameRecords = this.gameRecordRepository.findByUserId(userId);
        List<GameRecord> gameRecordList = new ArrayList<>();
        gameRecords.forEach(gameRecordList::add);
        return gameRecordList;
    }
}
