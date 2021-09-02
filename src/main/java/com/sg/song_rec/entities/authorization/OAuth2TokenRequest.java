package com.sg.song_rec.entities.authorization;

import com.sg.song_rec.util.mappers.UrlFormValue;

/**
 * A class representing a request for an OAuth2 access token
 */
public class OAuth2TokenRequest {
    @UrlFormValue("grant_type")
    private static final String grantType = "authorization_code";
    @UrlFormValue("code")
    private String code;
    @UrlFormValue("redirect_uri")
    private String redirectUri;

    public OAuth2TokenRequest() {
    }

    public OAuth2TokenRequest(String code, String redirectUri) {
        this.code = code;
        this.redirectUri = redirectUri;
    }

    /**
     * Gets the grantType
     *
     * @return java.lang.String The grantType
     */
    public String getGrantType() {
        return grantType;
    }

    /**
     * Gets the code
     *
     * @return java.lang.String The code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the code
     *
     * @param code The code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets the redirectUri
     *
     * @return java.lang.String The redirectUri
     */
    public String getRedirectUri() {
        return redirectUri;
    }

    /**
     * Sets the redirectUri
     *
     * @param redirectUri The redirectUri to set
     */
    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }
}
