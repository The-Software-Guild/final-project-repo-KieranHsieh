package com.sg.song_rec.controllers;

import com.sg.song_rec.entities.application.ExternalEndpoints;
import com.sg.song_rec.entities.application.ObjectSerializationException;
import com.sg.song_rec.entities.application.User;
import com.sg.song_rec.entities.authorization.OAuth2TokenRequest;
import com.sg.song_rec.entities.authorization.OAuth2TokenResponse;
import com.sg.song_rec.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("api")
public class AuthenticationController {
    public static final String ENDPOINT = "/api/";

    private static final String redirectUri = "http://localhost:8080/api/authenticate";
    @Value("${clientId}")
    private String clientId;
    @Value("${clientSecret}")
    private String clientSecret;

    private AuthenticationService service;

    private RestTemplate template;

    @Autowired
    public AuthenticationController(AuthenticationService service, RestTemplate template) {
        this.service = service;
        this.template = template;
        // Clear previous tokens
        service.removeExpiredUsers();
    }

    @GetMapping("/login")
    public RedirectView authenticate(HttpServletRequest request) {
        String userId = request.getSession().getId();
        User user = service.getUserById(userId);
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
        else if(service.isUserTokenExpired(user)) {

        }
        return getRedirectToEndpoint("");
    }

    @GetMapping("/authenticate")
    public RedirectView retrieveError(HttpServletRequest request) {
        String err = request.getParameter("error");
        String code = request.getParameter("code");

        if(err == null) {
            byte[] auth = Base64.getEncoder().encode((clientId + ":" + clientSecret).getBytes());
            String strAuth = new String(auth);
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

            } catch (ObjectSerializationException e) {
                return getRedirectToEndpoint("error");
            }
            return getRedirectToEndpoint("/recommendations");
        }
        return getRedirectToEndpoint("error");
    }

    @GetMapping("/")
    public RedirectView index(HttpServletRequest request) {
        User currentUser = service.getUserById(request.getSession().getId());

        if(currentUser != null) {
            return getRedirectToEndpoint("/recommendations");
        }
        return getRedirectToEndpoint("login");
    }

    @GetMapping("/error")
    public String errMsg() {
        return "Error";
    }

    @PatchMapping("/token/refresh")
    public String showMessage(HttpServletRequest request) {
        User currentUser = service.getUserById(request.getSession().getId());
        service.refreshTokenForUser(currentUser);
        return "Success";
    }

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

}
