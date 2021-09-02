package com.sg.song_rec.data.rest;

import com.sg.song_rec.data.UserAffinityDao;
import com.sg.song_rec.entities.application.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of UserAffinityDao using REST
 * as the method for getting UserAffinity information
 */
@Repository
public class UserAffinityRestDao extends RestDao implements UserAffinityDao {

    /**
     * The base endpoint used for all requests
     */
    public static final String ENDPOINT = "https://api.spotify.com/v1/me/top/";
    /**
     * The type appended to the base endpoint to create
     * base endpoints for artists
     */
    private static final String ARTIST_TYPE_VALUE = "artists";
    /**
     * The type appended to the base endpoint to create
     * base endpoints for tracks
     */
    private static final String TRACK_TYPE_VALUE = "tracks";

    /**
     * Creates a new UserAffinityRestDao object
     * @param template The RestTemplate object used when making requests
     */
    @Autowired
    public UserAffinityRestDao(RestTemplate template) {
        super(template);
    }

    /**
     * Gets the top artists for a given user in a time range
     * @param user  The user to retrieve information from
     * @param limit The number of artists to retrieve
     * @param range The time range used to calculate the most listened to artists
     * @return A list of artists
     */
    @Override
    public List<Artist> getTopArtists(User user, int limit, AffinityTimeRange range) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(getEndpointBaseForArtists());
        builder.queryParam("time_range", range.toString())
                .queryParam("limit", limit);
        ArtistList tracks = queryEndpoint(user, builder.toUriString(), ArtistList.class);
        if(tracks == null) {
            return new ArrayList<>();
        }
        return tracks.getArtists();
    }

    /**
     * Gets the top tracks for a given user
     * @param user  The user to retrieve information from
     * @param limit The number of tracks to retrieve
     * @param range The time range used to calculate the most listened to tracks
     * @return The top tracks for the user
     */
    @Override
    public List<Track> getTopTracks(User user, int limit, AffinityTimeRange range) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(getEndpointBaseForTracks());
        builder.queryParam("time_range", range.toString())
                .queryParam("limit", limit);
        TrackList tracks = queryEndpoint(user, builder.toUriString(), TrackList.class);
        if(tracks == null) {
            return new ArrayList<>();
        }
        return tracks.getTracks();
    }

    /**
     * Gets the base endpoint used for retrieving artists
     * @return The URL string endpoint for artists
     */
    private static String getEndpointBaseForArtists() {
        return ENDPOINT + ARTIST_TYPE_VALUE;
    }

    /**
     * Gets the base endpoint used for retrieving tracks
     * @return The URL string endpoint for tracks
     */
    private static String getEndpointBaseForTracks() {
        return ENDPOINT + TRACK_TYPE_VALUE;
    }
}
