package com.sg.song_rec.entities.authorization;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OAuth2TokenResponse {
    private String accessToken;
    private static final String tokenType = "Bearer";
    private String scope;
    private int expiresIn;
    private String refreshToken;

    /**
     * Gets the accessToken
     *
     * @return java.lang.String The accessToken
     */
    @JsonProperty("access_token")
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
     * Gets the tokenType
     *
     * @return java.lang.String The tokenType
     */
    @JsonProperty("token_type")
    public String getTokenType() {
        return tokenType;
    }

    /**
     * Gets the scope
     *
     * @return java.lang.String The scope
     */
    @JsonProperty("scope")
    public String getScope() {
        return scope;
    }

    /**
     * Sets the scope
     *
     * @param scope The scope to set
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * Gets the expiresIn
     *
     * @return int The expiresIn
     */
    @JsonProperty("expires_in")
    public int getExpiresIn() {
        return expiresIn;
    }

    /**
     * Sets the expiresIn
     *
     * @param expiresIn The expiresIn to set
     */
    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    /**
     * Gets the refreshToken
     *
     * @return java.lang.String The refreshToken
     */
    @JsonProperty("refresh_token")
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

}
