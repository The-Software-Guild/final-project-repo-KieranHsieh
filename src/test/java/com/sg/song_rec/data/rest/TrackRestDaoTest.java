package com.sg.song_rec.data.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sg.song_rec.data.TrackDao;
import com.sg.song_rec.entities.application.Track;
import com.sg.song_rec.entities.application.TrackList;
import com.sg.song_rec.entities.application.User;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@SpringBootTest
class TrackRestDaoTest {

    @Autowired
    AuthenticationService service;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    TrackDao trackDao;

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
    void getTrack() throws JsonProcessingException {
        Track testTrack = new Track();
        testTrack.setName("track");
        testTrack.setId("1");
        mockServer.expect(ExpectedCount.once(),
                requestTo(TrackRestDao.ENDPOINT + "/1"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(new ObjectMapper().writeValueAsString(testTrack)));
        Track retrieved = trackDao.getTrack(globUser, "1");
        mockServer.verify();
        assertEquals(testTrack, retrieved);
    }

    @Test
    void getTracks() throws JsonProcessingException {
        TrackList trackList = new TrackList();
        trackList.setTracks(new ArrayList<>());
        Track testTrack = new Track();
        testTrack.setName("track");
        testTrack.setId("1");
        Track testTrack2 = new Track();
        testTrack2.setName("track2");
        testTrack2.setId("2");
        trackList.getTracks().add(testTrack);
        trackList.getTracks().add(testTrack2);
        mockServer.expect(ExpectedCount.once(),
                requestTo(TrackRestDao.ENDPOINT + "?ids=1,2"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(new ObjectMapper().writeValueAsString(trackList)));
        List<Track> retrieved = trackDao.getTracks(globUser, Arrays.asList("1", "2"));
        mockServer.verify();
        assertEquals(retrieved.size(), 2);
        assertEquals(retrieved.get(0), testTrack);
        assertEquals(retrieved.get(1), testTrack2);
    }
}