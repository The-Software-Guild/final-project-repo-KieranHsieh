package com.sg.song_rec.entities.application;

/**
 * A utility class used to store endpoint URLs
 * that are accessed from multiple locations
 */
public class ExternalEndpoints {

    public static final String SPOTIFY_AUTHORIZATION_BASE = "https://accounts.spotify.com/authorize";
    public static final String SPOTIFY_TOKEN_REQUEST = "https://accounts.spotify.com/api/token";

    private ExternalEndpoints() {}
}
