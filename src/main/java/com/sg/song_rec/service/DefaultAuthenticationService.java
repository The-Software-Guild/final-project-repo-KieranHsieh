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

@Service
public class DefaultAuthenticationService implements AuthenticationService {

    private UserDao dao;
    private RestTemplate template;

    @Autowired
    public DefaultAuthenticationService(UserDao dao, RestTemplate template) {
        this.template = template;
        this.dao = dao;
    }

    @Override
    public void addUser(User user) {
        dao.addUser(user);
    }

    @Override
    public OAuth2Token getTokenFromResponse(OAuth2TokenResponse response) {
        OAuth2Token token = new OAuth2Token();
        token.setRefreshToken(response.getRefreshToken());
        token.setAccessToken(response.getAccessToken());
        token.setExpirationDate(LocalDateTime.now().plusSeconds(response.getExpiresIn()));
        return token;
    }

    @Override
    public void removeUserById(String id) {
        dao.removeUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return dao.getAllUsers();
    }

    @Override
    public boolean refreshTokenForUser(User user) {
        String refreshToken = user.getToken().getRefreshToken();
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

    @Override
    public User getUserById(String id) {
        return dao.getUserById(id);
    }

    @Override
    public boolean isUserTokenExpired(User user) {
        return !LocalDateTime.now().isBefore(user.getToken().getExpirationDate());
    }

    @Override
    public void removeExpiredUsers() {
        dao.removeExpiredUsers();
    }
}
