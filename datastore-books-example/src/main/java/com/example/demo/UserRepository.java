package com.example.demo;

import com.google.cloud.spring.data.datastore.repository.DatastoreRepository;

import java.util.List;

/**
 * Repository interface for accessing and managing User entities in a datastore.
 */
public interface UserRepository extends DatastoreRepository<User, Long> {

}
