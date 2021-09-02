package com.sg.song_rec.data.rest;

import com.sg.song_rec.data.UserAffinityDao;
import com.sg.song_rec.entities.application.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserAffinityRestDao extends RestDao implements UserAffinityDao {

    public static final String ENDPOINT = "https://api.spotify.com/v1/me/top/";
    private static final String ARTIST_TYPE_VALUE = "artists";
    private static final String TRACK_TYPE_VALUE = "tracks";

    @Autowired
    public UserAffinityRestDao(RestTemplate template) {
        super(template);
    }

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

    private static String getEndpointBaseForArtists() {
        return ENDPOINT + ARTIST_TYPE_VALUE;
    }
    private static String getEndpointBaseForTracks() {
        return ENDPOINT + TRACK_TYPE_VALUE;
    }
}
