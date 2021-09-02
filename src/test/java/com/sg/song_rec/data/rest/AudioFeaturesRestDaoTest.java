package com.sg.song_rec.data.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sg.song_rec.data.AudioFeaturesDao;
import com.sg.song_rec.entities.application.AudioFeatures;
import com.sg.song_rec.entities.application.AudioFeaturesList;
import com.sg.song_rec.entities.application.Track;
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
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AudioFeaturesRestDaoTest {

    @Autowired
    AuthenticationService service;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    AudioFeaturesDao dao;

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

    private AudioFeatures getNewAudioFeatures() {
        AudioFeatures features = new AudioFeatures();
        features.setAcousticness(1);
        features.setValence(2);
        features.setTempo(3);
        features.setSpeechiness(4);
        features.setMode(5);
        features.setLoudness(6);
        features.setLiveness(7);
        features.setKey(8);
        features.setInstrumentalness(9);
        features.setEnergy(10);
        features.setDanceability(11);
        return features;
    }

    @Test
    void getAudioFeaturesForTrack() throws JsonProcessingException {
        AudioFeatures testFeatures = getNewAudioFeatures();
        mockServer.expect(ExpectedCount.once(),
                requestTo(AudioFeaturesRestDao.ENDPOINT + "/1"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ObjectMapper().writeValueAsString(testFeatures)));
        Track testTrack = new Track();
        testTrack.setId("1");
        testTrack.setName("Name");
        AudioFeatures features = dao.getAudioFeaturesForTrack(globUser, testTrack);
        mockServer.verify();
        assertEquals(features, testFeatures);
    }

    @Test
    void getAudioFeaturesForTracks() throws JsonProcessingException {
        AudioFeaturesList audioFeaturesList = new AudioFeaturesList();
        audioFeaturesList.setAudioFeatures(new ArrayList<>());
        audioFeaturesList.getAudioFeatures().add(getNewAudioFeatures());
        audioFeaturesList.getAudioFeatures().add(getNewAudioFeatures());
        mockServer.expect(ExpectedCount.once(),
                requestTo(AudioFeaturesRestDao.ENDPOINT + "?ids=1,2"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(new ObjectMapper().writeValueAsString(audioFeaturesList)));
        Track testTrack = new Track();
        testTrack.setId("1");
        testTrack.setName("Name");
        Track testTrack2 = new Track();
        testTrack2.setId("2");
        testTrack2.setName("Name");
        List<AudioFeatures> features = dao.getAudioFeaturesForTracks(globUser, Arrays.asList(testTrack, testTrack2));
        mockServer.verify();
        assertEquals(2, features.size());
        assertEquals(audioFeaturesList.getAudioFeatures().get(0), features.get(0));
        assertEquals(audioFeaturesList.getAudioFeatures().get(1), features.get(1));
    }
}