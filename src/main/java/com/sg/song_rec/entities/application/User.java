package com.sg.song_rec.entities.application;

import com.sg.song_rec.entities.authorization.OAuth2Token;

import java.util.Objects;

public class User {
    private String id;
    private OAuth2Token token;

    /**
     * Gets the id
     *
     * @return java.lang.String The id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id
     *
     * @param id The id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the token
     *
     * @return com.sg.song_rec.entities.authorization.OAuth2Token The token
     */
    public OAuth2Token getToken() {
        return token;
    }

    /**
     * Sets the token
     *
     * @param token The token to set
     */
    public void setToken(OAuth2Token token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId()) &&
                Objects.equals(getToken(), user.getToken());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getToken());
    }
}
