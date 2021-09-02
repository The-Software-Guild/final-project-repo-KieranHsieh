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

    OAuth2Token getTokenFromResponse(OAuth2TokenResponse response);

    List<User> getAllUsers();
    void removeUserById(String id);

    User getUserById(String id);
    boolean isUserTokenExpired(User user);
    boolean refreshTokenForUser(User user);
    void removeExpiredUsers();
}
