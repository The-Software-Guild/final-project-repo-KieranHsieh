package com.sg.song_rec.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sg.song_rec.entities.application.ExternalEndpoints;
import com.sg.song_rec.entities.application.User;
import com.sg.song_rec.entities.authorization.OAuth2Token;
import com.sg.song_rec.entities.authorization.OAuth2TokenResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DefaultAuthenticationServiceTest {

    @Autowired
    AuthenticationService service;

    @Autowired
    RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        List<User> users = service.getAllUsers();
        for(User u : users) {
            service.removeUserById(u.getId());
        }
    }

    @Test
    void testAddAndGetUser() {
        User u = new User();
        OAuth2Token token = new OAuth2Token();
        token.setAccessToken("access");
        token.setRefreshToken("refresh");
        token.setExpirationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        u.setId("1");
        u.setToken(token);
        assertNull(service.getUserById("1"));
        service.addUser(u);
        User retrieved = service.getUserById("1");
        assertNotNull(retrieved);
        assertEquals(retrieved, u);
    }

    @Test
    void getTokenFromResponse() {
        OAuth2TokenResponse response = new OAuth2TokenResponse();
        OAuth2Token token = service.getTokenFromResponse(response);
        assertEquals(token.getAccessToken(), response.getAccessToken());
        assertEquals(token.getRefreshToken(), response.getRefreshToken());
    }

    @Test
    void removeUserById() {
        User u = new User();
        OAuth2Token token = new OAuth2Token();
        token.setAccessToken("access");
        token.setRefreshToken("refresh");
        token.setExpirationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        u.setId("1");
        u.setToken(token);
        service.addUser(u);
        assertNotNull(service.getUserById("1"));
        service.removeUserById("1");
        assertNull(service.getUserById("1"));
    }

    @Test
    void refreshTokenForUser() throws JsonProcessingException {
        OAuth2TokenResponse response = new OAuth2TokenResponse();
        response.setAccessToken("access");
        response.setRefreshToken("refresh");
        response.setExpiresIn(1);
        response.setScope("scope");
        mockServer.expect(ExpectedCount.once(),
                requestTo(ExternalEndpoints.SPOTIFY_TOKEN_REQUEST))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ObjectMapper().writeValueAsString(response)));
        User u = new User();
        u.setId("1");
        OAuth2Token token = new OAuth2Token();
        token.setAccessToken("access");
        token.setRefreshToken("refresh");
        token.setExpirationDate(LocalDateTime.now());
        u.setToken(token);
        assertTrue(service.refreshTokenForUser(u));
        mockServer.verify();

    }

    @Test
    void isUserTokenExpired() {
        User user = new User();
        user.setId("1");
        OAuth2Token token = new OAuth2Token();
        token.setExpirationDate(LocalDateTime.MIN);
        token.setAccessToken("access");
        token.setRefreshToken("refresh");
        user.setToken(token);
        assertTrue(service.isUserTokenExpired(user));
        token.setExpirationDate(LocalDateTime.MAX);
        assertFalse(service.isUserTokenExpired(user));
    }
}