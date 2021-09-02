package com.sg.song_rec.entities.application;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;

public class ArtistList {
    @JsonAlias("items")
    private List<Artist> artists;

    /**
     * Gets the artists
     *
     * @return java.util.List<com.sg.song_rec.entities.application.Artist> The artists
     */
    public List<Artist> getArtists() {
        return artists;
    }

    /**
     * Sets the artists
     *
     * @param artists The artists to set
     */
    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }
}
