package com.sg.song_rec.entities.application;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.Objects;

public class UserProfile {
    @JsonAlias("display_name")
    private String displayName;

    /**
     * Gets the displayName
     *
     * @return java.lang.String The displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the displayName
     *
     * @param displayName The displayName to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfile profile = (UserProfile) o;
        return Objects.equals(getDisplayName(), profile.getDisplayName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDisplayName());
    }
}
