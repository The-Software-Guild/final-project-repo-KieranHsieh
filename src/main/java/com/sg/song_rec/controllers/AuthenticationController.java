package com.sg.song_rec.controllers;

import com.sg.song_rec.entities.application.ExternalEndpoints;
import com.sg.song_rec.entities.application.ObjectSerializationException;
import com.sg.song_rec.entities.application.User;
import com.sg.song_rec.entities.authorization.OAuth2TokenRequest;
import com.sg.song_rec.entities.authorization.OAuth2TokenResponse;
import com.sg.song_rec.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

/**
 * The controller for the api endpoints that control
 * authentication with the Spotify web API
 */
@RestController
@RequestMapping("api")
public class AuthenticationController {
    public static final String ENDPOINT = "/api/";

    @Value("${redirectUri:http://localhost:8080/api/authenticate}")
    private String redirectUri;
    @Value("${clientId}")
    private String clientId;
    @Value("${clientSecret}")
    private String clientSecret;

    private AuthenticationService service;

    private RestTemplate template;

    /**
     * Constructs an AuthenticationController object and clears all expired tokens from the database
     * @param service The authentication service to use
     * @param template The RestTemplate to use
     */
    @Autowired
    public AuthenticationController(AuthenticationService service, RestTemplate template) {
        this.service = service;
        this.template = template;
        // Clear previous tokens
        service.removeExpiredUsers();
    }

    /**
     * The login endpoint for getting access tokens for the spotify API
     * @param request The request object automatically passed to the endpoint
     * @return A redirect view that redirects either to the authorization or base API endpoint
     */
    @GetMapping("/login")
    public RedirectView authenticate(HttpServletRequest request) {
        String userId = request.getSession().getId();
        User user = service.getUserById(userId);

        // If the user session is not associated with an endpoint, we begin the authorization process
        if(user == null) {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(ExternalEndpoints.SPOTIFY_AUTHORIZATION_BASE);
            builder.queryParam("client_id", clientId)
                    .queryParam("response_type", "code")
                    .queryParam("show_dialog", false)
                    .queryParam("scope", "user-top-read user-read-private")
                    .queryParam("redirect_uri", redirectUri);

            RedirectView view = new RedirectView();
            view.setUrl(builder.toUriString());
            return view;
        }
        // We have a user, but the token is expired
        else if(service.isUserTokenExpired(user)) {
            service.refreshTokenForUser(user);
        }
        return getRedirectToEndpoint("");
    }

    /**
     * The authentication endpoint redirected to by spotify
     * @param request The request object automatically passed to the method
     * @return A redirect view to either the error or recommendation endpoints
     */
    @GetMapping("/authenticate")
    public RedirectView retrieveError(HttpServletRequest request) {
        String err = request.getParameter("error");
        String code = request.getParameter("code");
        // If an error occurred with sending data to Spotify from the login endpoint
        if(err != null) {
            return getRedirectToEndpoint("error");
        }
        // Encode the client id and secret as specified in
        byte[] auth = Base64.getEncoder().encode((clientId + ":" + clientSecret).getBytes());
        String strAuth = new String(auth);

        // Build the token request
        HttpRequestBuilder builder = new HttpRequestBuilder(template);
        try {
            OAuth2TokenResponse response = builder.setContentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .setEndpoint(ExternalEndpoints.SPOTIFY_TOKEN_REQUEST)
                    .setMethod(HttpMethod.POST)
                    .setBody(new OAuth2TokenRequest(code, redirectUri))
                    .addHeader("Authorization", "Basic " + strAuth)
                    .exchange(OAuth2TokenResponse.class).getBody();
            User user = new User();
            user.setId(request.getSession().getId());
            user.setToken(service.getTokenFromResponse(response));
            service.addUser(user);
        }
        catch (ObjectSerializationException e) {
            return getRedirectToEndpoint("error");
        }
        return getRedirectToEndpoint("/recommendations");
    }

    /**
     * The endpoint for the base API. The endpoint is a utility endpoint that will
     * either redirect to the recommendations or login endpoints
     * @param request The request automatically passed to the method
     * @return A RedirectView pointing towards the recommendations or login endpoints
     */
    @GetMapping("/")
    public RedirectView getBaseApiPage(HttpServletRequest request) {
        User currentUser = service.getUserById(request.getSession().getId());

        if(currentUser != null) {
            return getRedirectToEndpoint("/recommendations");
        }
        return getRedirectToEndpoint("login");
    }

    /**
     * The generic error endpoint. This contains a user facing error message and a link back to the landing page
     * @return The HTML output of the page
     */
    @GetMapping("/error")
    public String errMsg() {
        return "An error has occured. Please go to <a href='/'>here</a> to return to the main page.";
    }

    /**
     * The token refresh endpoint. This endpoint refreshes the access token for the current user
     * @param request The request automatically passed to the method
     * @return Returns a page with the body "Success", but does NOT redirect to the previous page
     */
    @PatchMapping("/token/refresh")
    public String showMessage(HttpServletRequest request) {
        User currentUser = service.getUserById(request.getSession().getId());
        service.refreshTokenForUser(currentUser);
        return "Success";
    }

    /**
     * Generates a RedirectView object pointing to the parameterized endpoint
     * @param endpoint The endpoint to redirect to
     * @return The created RedirectView object
     */
    private RedirectView getRedirectToEndpoint(String endpoint) {
        RedirectView view = new RedirectView();
        view.setUrl(endpoint);
        return view;
    }

    /**
     * Gets the clientId
     *
     * @return java.lang.String The clientId
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Sets the clientId
     *
     * @param clientId The clientId to set
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * Gets the clientSecret
     *
     * @return java.lang.String The clientSecret
     */
    public String getClientSecret() {
        return clientSecret;
    }

    /**
     * Sets the clientSecret
     *
     * @param clientSecret The clientSecret to set
     */
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
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
     * @param redirectUri The redirectUri to set
     */
    void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }
}
