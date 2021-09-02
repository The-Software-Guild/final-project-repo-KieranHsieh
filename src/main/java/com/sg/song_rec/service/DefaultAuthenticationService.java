package com.sg.song_rec.service;

import com.sg.song_rec.controllers.HttpRequestBuilder;
import com.sg.song_rec.data.UserDao;
import com.sg.song_rec.entities.application.ExternalEndpoints;
import com.sg.song_rec.entities.application.ObjectSerializationException;
import com.sg.song_rec.entities.application.User;
import com.sg.song_rec.entities.authorization.OAuth2Token;
import com.sg.song_rec.entities.authorization.OAuth2TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * An implementation of AuthenticationService
 * with default behavior
 */
@Service
public class DefaultAuthenticationService implements AuthenticationService {

    private UserDao dao;
    private RestTemplate template;

    /**
     * Constructs a new DefaultAuthenticationService
     * @param dao The UserDao used to manage users
     * @param template The RestTemplate used to make requests
     */
    @Autowired
    public DefaultAuthenticationService(UserDao dao, RestTemplate template) {
        this.template = template;
        this.dao = dao;
    }

    /**
     * @param user The user to register
     */
    @Override
    public void addUser(User user) {
        dao.addUser(user);
    }

    /**
     * Gets all users
     * @return All users
     */
    @Override
    public List<User> getAllUsers() {
        return dao.getAllUsers();
    }

    /**
     * Removes a user given their ID
     * @param id The ID of the user
     */
    @Override
    public void removeUserById(String id) {
        dao.removeUserById(id);
    }

    /**
     * @param id The ID of th user to retrieve
     * @return The retrieved user if they exist, or null otherwise
     */
    @Override
    public User getUserById(String id) {
        return dao.getUserById(id);
    }

    /**
     * Checks if a user's token is expired
     * @param user The user to check the token for
     * @return If the user's token is expired
     */
    @Override
    public boolean isUserTokenExpired(User user) {
        return !LocalDateTime.now().isBefore(user.getToken().getExpirationDate());
    }

    /**
     * Refreshes a token for a given user
     * @param user The user whose token is being refreshed
     * @return If the user's token was successfully refreshed
     */
    @Override
    public boolean refreshTokenForUser(User user) {
        HttpRequestBuilder builder = new HttpRequestBuilder(template);
        try {
            OAuth2Token newToken = getTokenFromResponse(Objects.requireNonNull(builder.setEndpoint(ExternalEndpoints.SPOTIFY_TOKEN_REQUEST)
                    .setContentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .setMethod(HttpMethod.POST)
                    .setBody(user.getToken())
                    .exchange(OAuth2TokenResponse.class).getBody()));
            user.setToken(newToken);
            dao.updateUser(user);
            return true;
        }
        catch(ObjectSerializationException e) {
            return false;
        }
    }

    /**
     * Removes all users where isUserTokenExpired returns true
     */
    @Override
    public void removeExpiredUsers() {
        dao.removeExpiredUsers();
    }

    /**
     * Creates an OAuth2Token given an OAuth2TokenResponse object
     * @param response The response to create a token from
     * @return The created token
     */
    @Override
    public OAuth2Token getTokenFromResponse(OAuth2TokenResponse response) {
        OAuth2Token token = new OAuth2Token();
        token.setRefreshToken(response.getRefreshToken());
        token.setAccessToken(response.getAccessToken());
        token.setExpirationDate(LocalDateTime.now().plusSeconds(response.getExpiresIn()));
        return token;
    }
}
