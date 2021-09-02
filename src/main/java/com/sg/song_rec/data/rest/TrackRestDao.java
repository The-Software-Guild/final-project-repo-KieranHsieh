package com.sg.song_rec.data.rest;

import com.sg.song_rec.controllers.HttpRequestBuilder;
import com.sg.song_rec.data.TrackDao;
import com.sg.song_rec.entities.application.AudioFeatures;
import com.sg.song_rec.entities.application.Track;
import com.sg.song_rec.entities.application.TrackList;
import com.sg.song_rec.entities.application.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class TrackRestDao extends RestDao implements TrackDao {

    public static final String ENDPOINT = "https://api.spotify.com/v1/tracks";

    @Autowired
    public TrackRestDao(RestTemplate template) {
        super(template);
    }

    @Override
    public Track getTrack(User user, String id) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(ENDPOINT + "/" + id);
        return queryEndpoint(user, uriComponentsBuilder.toUriString(), Track.class);
    }

    @Override
    public List<Track> getTracks(User user, List<String> ids) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT);
        uriBuilder.queryParam("ids", String.join(",", ids));
        TrackList tracks = queryEndpoint(user, uriBuilder.toUriString(), TrackList.class);
        if(tracks == null) {
            return new ArrayList<>();
        }
        return tracks.getTracks();
    }
}
