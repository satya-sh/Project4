package com.example.demo;

import java.util.List;

import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;

/**
 * Repository interface for User entities.
 */
public interface UserRepository extends DatastoreRepository<User, String> {

    List<User> findByUserHandle(String userHandle);
    // Custom query methods if needed
}
