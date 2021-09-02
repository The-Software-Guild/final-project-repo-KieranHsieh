package com.sg.song_rec.entities.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * An entity representing a unique artist
 */
public class Artist extends UniqueEntity {
    private String name;
    private List<String> genres = new ArrayList<>();

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
     * Gets the genres
     *
     * @return java.util.List<java.lang.String> The genres
     */
    public List<String> getGenres() {
        return genres;
    }

    /**
     * Sets the genres
     *
     * @param genres The genres to set
     */
    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artist artist = (Artist) o;
        return Objects.equals(getName(), artist.getName()) &&
                Objects.equals(getGenres(), artist.getGenres());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getGenres());
    }
}
