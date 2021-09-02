package com.sg.song_rec.data.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sg.song_rec.data.UserAffinityDao;
import com.sg.song_rec.data.UserProfileDao;
import com.sg.song_rec.entities.application.User;
import com.sg.song_rec.entities.application.UserProfile;
import com.sg.song_rec.entities.authorization.OAuth2Token;
import com.sg.song_rec.service.AuthenticationService;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@SpringBootTest
class UserProfileRestDaoTest {

    @Autowired
    AuthenticationService service;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private UserProfileDao userProfileDao;

    private MockRestServiceServer mockServer;
    private User globUser;

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        List<User> users = service.getAllUsers();
        for(User u : users) {
            service.removeUserById(u.getId());
        }
        globUser = new User();
        globUser.setId("1");
        OAuth2Token token = new OAuth2Token();
        token.setExpirationDate(LocalDateTime.now());
        token.setRefreshToken("refresh");
        token.setAccessToken("access");
        globUser.setToken(token);
        service.addUser(globUser);
    }

    @Test
    void getUserProfile() throws JsonProcessingException {
        UserProfile profile = new UserProfile();
        profile.setDisplayName("name");
        mockServer.expect(ExpectedCount.once(),
                requestTo(UserProfileRestDao.ENDPOINT))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(new ObjectMapper().writeValueAsString(profile)));
        UserProfile retrieved = userProfileDao.getUserProfile(globUser);
        mockServer.verify();
        assertEquals(retrieved, profile);
    }
}