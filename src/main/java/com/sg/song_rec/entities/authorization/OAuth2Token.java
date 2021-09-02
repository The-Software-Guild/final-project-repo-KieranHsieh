package com.sg.song_rec.entities.authorization;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * A class representing an OAuth2 access token
 */
public class OAuth2Token {
    private String accessToken;
    private String refreshToken;
    private LocalDateTime expirationDate;

    /**
     * Gets the accessToken
     *
     * @return java.lang.String The accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Sets the accessToken
     *
     * @param accessToken The accessToken to set
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Gets the refreshToken
     *
     * @return java.lang.String The refreshToken
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * Sets the refreshToken
     *
     * @param refreshToken The refreshToken to set
     */
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * Gets the expirationDate
     *
     * @return java.time.LocalDateTime The expirationDate
     */
    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets the expirationDate
     *
     * @param expirationDate The expirationDate to set
     */
    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OAuth2Token token = (OAuth2Token) o;
        return Objects.equals(getAccessToken(), token.getAccessToken()) &&
                Objects.equals(getRefreshToken(), token.getRefreshToken());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccessToken(), getRefreshToken());
    }
}
