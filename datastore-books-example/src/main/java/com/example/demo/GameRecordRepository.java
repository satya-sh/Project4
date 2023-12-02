package com.example.demo;

import java.util.List;

import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;

import org.springframework.data.domain.Pageable;

/**
 * Repository interface for GameRecord entities.
 */
public interface GameRecordRepository extends DatastoreRepository<GameRecord, Long> {
    List<GameRecord> findByUserGoogleId(String userGoogleId, Pageable pageable);

    List<GameRecord> findTopByOrderByGameScoreDesc(Pageable pageable);
}
