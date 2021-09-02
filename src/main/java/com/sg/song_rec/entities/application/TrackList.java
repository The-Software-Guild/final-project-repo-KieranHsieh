package com.sg.song_rec.entities.application;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

/**
 * A utility class used when de-serializing
 * a JSON array of Track objects
 */
public class TrackList {
    @JsonAlias("items")
    private List<Track> tracks;

    /**
     * Gets the tracks
     *
     * @return java.util.List<com.sg.song_rec.entities.application.Track> The tracks
     */
    public List<Track> getTracks() {
        return tracks;
    }

    /**
     * Sets the tracks
     *
     * @param tracks The tracks to set
     */
    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }
}
