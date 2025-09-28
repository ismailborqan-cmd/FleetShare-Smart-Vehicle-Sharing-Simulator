package com.ismail.fleetShare.infrastructure;

import com.ismail.fleetShare.domain.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * In-memory repository for storing and managing users.
 * Uses a HashMap internally to store users by their unique ID.
 */
public class InMemoryUserRepository {

    private final Map<String, User> users = new HashMap<>();

    /**
     * Saves a user to the repository.
     * If a user with the same ID exists, it will be replaced.
     *
     * @param user the user to save
     */
    public void save(User user) {
        users.put(user.getId(), user);
    }

    /**
     * Finds a user by their unique ID.
     *
     * @param id the ID of the user to find
     * @return the user with the given ID, or null if not found
     */
    public User findById(String id) {
        return users.get(id);
    }

    /**
     * Returns all users stored in the repository.
     *
     * @return a list of all users
     */
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }
}
