package com.sg.song_rec.data.rest;

import com.sg.song_rec.controllers.HttpRequestBuilder;
import com.sg.song_rec.entities.application.TrackList;
import com.sg.song_rec.entities.application.User;
import com.sg.song_rec.entities.authorization.AuthenticationException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public abstract class RestDao {

    protected RestTemplate template;

    public RestDao(RestTemplate template) {
        this.template = template;
    }

    public <T> T queryEndpoint(User user, String endpoint, Class<T> retClass) {
        HttpRequestBuilder builder = new HttpRequestBuilder(template);

        ResponseEntity<T> tracks = builder.setMethod(HttpMethod.GET)
                .addHeader("Authorization", "Bearer " + user.getToken().getAccessToken())
                .setEndpoint(endpoint)
                .exchange(retClass);
        if(tracks.getBody() == null) {
            return null;
        }
        return tracks.getBody();
    }
}
