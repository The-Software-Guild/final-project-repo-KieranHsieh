package com.sg.song_rec.data.rest;

import com.sg.song_rec.data.RecommendationDao;
import com.sg.song_rec.entities.application.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * An implementation of RecommendationDao using REST
 * as the method for getting recommendations
 */
@Repository
public class RecommendationRestDao extends RestDao implements RecommendationDao {

    /**
     * The base endpoint used to get recommendations
     */
    private static final String ENDPOINT = "https://api.spotify.com/v1/recommendations";

    /**
     * Constructs a new RecommendationRestDao
     * @param template The RestTemplate used to make requests
     */
    @Autowired
    public RecommendationRestDao(RestTemplate template) {
        super(template);
    }

    /**
     * Gets recommendations for a specified user
     * @param user           The user used to authenticate requests for recommendations
     * @param limit          The number of recommendations to retrieve
     * @param artists        A list of artists used when retrieving recommendations. The artists
     *                       provided in the parameter are NOT guaranteed to all be used in the final
     *                       request
     * @param tracks         A list of tracks used when retrieving recommendations. The tracks
     *                       provided in the parameter are NOT guaranteed to all be used in the final
     *                       request
     * @param genres         A list of genres used when retrieving recommendations. The genres provided
     *                       in the parameter are NOT guaranteed to all be used in the final request
     * @param targetFeatures An AudioFeatures object with values representing target audio feature values
     *                       for the retrieved recommendations
     * @return The generated recommendations
     */
    @Override
    public List<Track> getRecommendations(User user, int limit, List<Artist> artists, List<Track> tracks, List<String> genres, AudioFeatures targetFeatures) {
        // Build the request
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(ENDPOINT);
        builder.queryParam("limit", limit)
                .queryParam("seed_artists", joinList(artists, 1)) // Take one artist
                .queryParam("seed_tracks", joinList(tracks, 2)) // Take 2 tracks
                .queryParam("seed_genres", joinStrList(genres, 2)); // Take 2 seed genres

        // If the given target features is null, we don't need to set
        // the optional query parameters
        if(targetFeatures != null) {
                builder.queryParam("target_acousticness", targetFeatures.getAcousticness())
                .queryParam("target_danceability", targetFeatures.getDanceability())
                .queryParam("target_energy", targetFeatures.getEnergy())
                .queryParam("target_instrumentalness", targetFeatures.getInstrumentalness())
                .queryParam("target_key", targetFeatures.getKey())
                .queryParam("target_liveness", targetFeatures.getLiveness())
                .queryParam("target_loudness", targetFeatures.getLoudness())
                .queryParam("target_mode", targetFeatures.getMode())
                .queryParam("target_speechiness", targetFeatures.getSpeechiness())
                .queryParam("target_tempo", targetFeatures.getTempo())
                .queryParam("target_valence", targetFeatures.getValence());
        }

        // Make the request
        TrackList recs = queryEndpoint(user, builder.toUriString(), TrackList.class);
        if(recs == null) {
            return new ArrayList<>();
        }
        return recs.getTracks();
    }

    /**
     * Joins a list of strings into a single string with a comma delimiter.
     * The number of elements in the final string is denoted with the limit parameter
     * @param entities The entities to join into a string
     * @param limit The maximum number of entities to join
     * @return The final joined string
     */
    private String joinStrList(List<String> entities, int limit) {
        StringJoiner joiner = new StringJoiner(",");
        int targetSize = Math.min(entities.size(), limit);
        for(int i = 0;i < targetSize; i ++) {
            joiner.add(entities.get(i));
        }
        return joiner.toString();
    }

    /**
     * Generates a joined string given a list of UniqueEntities
     * @param entities The entities to join into a string
     * @param limit THe maximum number of entities to join
     * @param <T> The type of the entity that is being joined. This type must extend UniqueEntity
     * @return The joined string
     */
    private <T extends UniqueEntity> String joinList(List<T> entities, int limit) {
        StringJoiner joiner = new StringJoiner(",");
        int targetSize = Math.min(entities.size(), limit);
        for(int i = 0; i < targetSize; i ++) {
            joiner.add(entities.get(i).getId());
        }
        return joiner.toString();
    }
}
