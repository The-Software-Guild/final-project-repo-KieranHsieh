package com.sg.song_rec.data.rest;

import com.sg.song_rec.data.AudioFeaturesDao;
import com.sg.song_rec.entities.application.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * An implementation of AudioFeaturesDao using
 * REST calls to retrieve AudioFeatures
 */
@Repository
public class AudioFeaturesRestDao extends RestDao implements AudioFeaturesDao {

    /**
     * The base endpoint for requests
     */
    public static final String ENDPOINT = "https://api.spotify.com/v1/audio-features";

    /**
     * Constructs a new AudioFeaturesRestDao
     * @param template The RestTemplate used to make requests
     */
    @Autowired
    public AudioFeaturesRestDao(RestTemplate template) {
        super(template);
    }

    /**
     * Gets audio features for a given track
     * @param user The user used to authenticate requests for audio features
     * @param track The track to get audio features for
     * @return The audio features for the track
     */
    @Override
    public AudioFeatures getAudioFeaturesForTrack(User user, Track track) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(ENDPOINT + "/" + track.getId());
        return queryEndpoint(user,  uriComponentsBuilder.toUriString(), AudioFeatures.class);
    }

    /**
     * Gets the audio features for multiple tracks
     * @param user The user used to authenticate requests for audio features
     * @param tracks The list of tracks to get audio features for
     * @return The audio features for the provided tracks
     */
    @Override
    public List<AudioFeatures> getAudioFeaturesForTracks(User user, List<Track> tracks) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(ENDPOINT);

        // Join track ids into a final string
        StringJoiner joiner = new StringJoiner(",");
        for(Track track : tracks) {
            joiner.add(track.getId());
        }

        // Add the joined string to the query parameters
        uriBuilder.queryParam("ids", joiner.toString());


        // Query the endpoint
        AudioFeaturesList features = queryEndpoint(user, uriBuilder.toUriString(), AudioFeaturesList.class);
        if(features == null) {
            return new ArrayList<>();
        }
        return features.getAudioFeatures();
    }
}
