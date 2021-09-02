package com.sg.song_rec.data.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sg.song_rec.data.AudioFeaturesDao;
import com.sg.song_rec.data.UserAffinityDao;
import com.sg.song_rec.entities.application.*;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserAffinityRestDaoTest {

    @Autowired
    AuthenticationService service;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private UserAffinityDao affinityDao;

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
    void getTopArtists() throws JsonProcessingException {
        ArtistList artists = new ArtistList();
        artists.setArtists(new ArrayList<>());
        Artist artist1 = new Artist();
        artist1.setId("1");
        artist1.setName("artist1");
        Artist artist2 = new Artist();
        artist2.setId("2");
        artist2.setName("artist2");
        artists.getArtists().add(artist1);
        artists.getArtists().add(artist2);
        mockServer.expect(ExpectedCount.once(),
                requestTo(UserAffinityRestDao.ENDPOINT + "artists?time_range=long_term&limit=5"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(new ObjectMapper().writeValueAsString(artists)));
        List<Artist> artistList = affinityDao.getTopArtists(globUser, 5, AffinityTimeRange.LONG_TERM);
        mockServer.verify();
        assertEquals(artistList.size(), 2);
        assertEquals(artist1, artistList.get(0));
        assertEquals(artist2, artistList.get(1));
    }

    @Test
    void getTopTracks() throws JsonProcessingException {
        TrackList trackList = new TrackList();
        trackList.setTracks(new ArrayList<>());
        Track track1 = new Track();
        track1.setId("1");
        track1.setName("track1");

        Track track2 = new Track();
        track2.setId("2");
        track2.setName("track2");
        trackList.getTracks().add(track1);
        trackList.getTracks().add(track2);

        mockServer.expect(ExpectedCount.once(),
                requestTo(UserAffinityRestDao.ENDPOINT + "tracks?time_range=long_term&limit=5"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(new ObjectMapper().writeValueAsString(trackList)));
        List<Track> tracks = affinityDao.getTopTracks(globUser, 5, AffinityTimeRange.LONG_TERM);
        mockServer.verify();
        assertEquals(tracks.size(), 2);
        assertEquals(tracks.get(0), track1);
        assertEquals(tracks.get(1), track2);
    }
}