package com.example.demo;

import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;
import java.util.List;

/**
 * Repository interface for accessing and managing GameRecord entities in a datastore.
 */
public interface GameRecordRepository extends DatastoreRepository<GameRecord, Long> {

    /**
     * Finds a list of game records based on the provided user ID.
     *
     * @param userId The user ID to search for.
     * @return A list of GameRecord entities with the given user ID.
     */
    List<GameRecord> findByUserId(String userId);

}
