package com.sg.song_rec.service;

import com.sg.song_rec.entities.application.User;
import com.sg.song_rec.entities.authorization.OAuth2Token;
import com.sg.song_rec.entities.authorization.OAuth2TokenResponse;

import java.util.List;

public interface AuthenticationService {
    /**
     * Registers a user with the system if it does not already exist
     * @param user The user to register
     */
    void addUser(User user);

    /**
     * Gets all users
     * @return ALl users
     */
    List<User> getAllUsers();

    /**
     * Removes a user given their ID
     * @param id The ID of the user
     */
    void removeUserById(String id);

    /**
     * Gets a user given their ID
     * @param id The ID of th user to retrieve
     * @return The user if they were found, or null if they were not
     */
    User getUserById(String id);

    /**
     * Checks if a user's token has expired
     * @param user The user to check the token for
     * @return If the user's token is expired
     */
    boolean isUserTokenExpired(User user);

    /**
     * Refreshes a user's token
     * @param user The user whose token is being refreshed
     * @return True if the token was refreshed successfully
     */
    boolean refreshTokenForUser(User user);

    /**
     * Removes all users where isUserTokenExpired returns true
     */
    void removeExpiredUsers();

    /**
     * Creates an OAuth2Token given an OAuth2TokenResponse object
     * @param response THe response to create a token from
     * @return The created OAuth2Token
     */
    OAuth2Token getTokenFromResponse(OAuth2TokenResponse response);
}
