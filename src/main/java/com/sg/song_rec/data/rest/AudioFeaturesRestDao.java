package com.sg.song_rec.data.rest;

import com.sg.song_rec.controllers.HttpRequestBuilder;
import com.sg.song_rec.data.AudioFeaturesDao;
import com.sg.song_rec.entities.application.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Repository
public class AudioFeaturesRestDao extends RestDao implements AudioFeaturesDao {

    public static final String ENDPOINT = "https://api.spotify.com/v1/audio-features";

    @Autowired
    public AudioFeaturesRestDao(RestTemplate template) {
        super(template);
    }

    @Override
    public AudioFeatures getAudioFeaturesForTrack(User user, Track track) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(ENDPOINT + "/" + track.getId());
        return queryEndpoint(user,  uriComponentsBuilder.toUriString(), AudioFeatures.class);
    }

    @Override
    public List<AudioFeatures> getAudioFeaturesForTracks(User user, List<Track> tracks) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT);
        StringJoiner joiner = new StringJoiner(",");
        for(Track track : tracks) {
            joiner.add(track.getId());
        }
        uriBuilder.queryParam("ids", joiner.toString());

        AudioFeaturesList features = queryEndpoint(user, uriBuilder.toUriString(), AudioFeaturesList.class);
        if(features == null) {
            return new ArrayList<>();
        }
        return features.getAudioFeatures();
    }
}
