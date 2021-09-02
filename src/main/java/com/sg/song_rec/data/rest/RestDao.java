package com.sg.song_rec.data.rest;

import com.sg.song_rec.controllers.HttpRequestBuilder;
import com.sg.song_rec.entities.application.User;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * An abstract class used to make generic REST get requests
 */
public abstract class RestDao {

    /**
     * The rest template used to make requests
     */
    protected RestTemplate template;

    /**
     * Constructs a new RestDao
     * @param template The rest template used to make requests
     */
    public RestDao(RestTemplate template) {
        this.template = template;
    }

    /**
     * Queries a given endpoint as a given user
     * @param user The user used to authenticate requests
     * @param endpoint The endpoint to query
     * @param retClass The returned object class
     * @param <T> The class of the returned object
     * @return The object retrieved from the endpoint
     */
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
