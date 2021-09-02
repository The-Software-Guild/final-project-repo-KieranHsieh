package com.sg.song_rec.data;

import com.sg.song_rec.entities.application.User;

import java.util.List;

/**
 * An interface for managing users
 */
public interface UserDao {
    /**
     * Retrieves a user given their id
     * @param id The user's ID
     * @return The retrieved user if they were found, or null otherwise
     */
    User getUserById(String id);

    /**
     * Adds a user
     * @param user The user to add
     */
    void addUser(User user);

    /**
     * Removes a user given their id
     * @param id The ID of the user to remove
     */
    void removeUserById(String id);

    /**
     * Updates a given user. The user to be updated
     * is determined by the ID of the parameterized user
     * @param user The new value of the user.
     */
    void updateUser(User user);

    /**
     * Gets all currently stored users
     * @return All users
     */
    List<User> getAllUsers();

    /**
     * Removes all users that have expired
     */
    void removeExpiredUsers();
}
