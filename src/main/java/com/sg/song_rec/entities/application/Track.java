package com.sg.song_rec.entities.application;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.List;
import java.util.Objects;

public class Track extends UniqueEntity {
    private String name;
    private List<Artist> artists;

    /**
     * Gets the name
     *
     * @return java.lang.String The name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name
     *
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the artists
     *
     * @return com.sg.song_rec.entities.application.ArtistList The artists
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Track track = (Track) o;
        return Objects.equals(getName(), track.getName()) &&
                Objects.equals(getArtists(), track.getArtists());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getArtists());
    }
}
