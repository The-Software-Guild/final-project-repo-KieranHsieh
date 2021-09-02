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

@Repository
public class RecommendationRestDao extends RestDao implements RecommendationDao {

    private static final String ENDPOINT = "https://api.spotify.com/v1/recommendations";

    @Autowired
    public RecommendationRestDao(RestTemplate template) {
        super(template);
    }

    @Override
    public List<Track> getRecommendations(User user, int limit, List<Artist> artists, List<Track> tracks, List<String> genres, AudioFeatures targetFeatures) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(ENDPOINT);
        builder.queryParam("limit", limit)
                .queryParam("seed_artists", joinList(artists, 1))
                .queryParam("seed_tracks", joinList(tracks, 3))
                .queryParam("seed_genres", joinStrList(genres, 1));
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
        TrackList recs = queryEndpoint(user, builder.toUriString(), TrackList.class);
        if(recs == null) {
            return new ArrayList<>();
        }
        return recs.getTracks();
    }

    private String joinStrList(List<String> entities, int limit) {
        StringJoiner joiner = new StringJoiner(",");
        int targetSize = Math.min(entities.size(), limit);
        for(int i = 0;i < targetSize; i ++) {
            joiner.add(entities.get(i));
        }
        return joiner.toString();
    }

    private <T extends UniqueEntity> String joinList(List<T> entities, int limit) {
        StringJoiner joiner = new StringJoiner(",");
        int targetSize = Math.min(entities.size(), limit);
        for(int i = 0; i < targetSize; i ++) {
            joiner.add(entities.get(i).getId());
        }
        return joiner.toString();
    }
}
