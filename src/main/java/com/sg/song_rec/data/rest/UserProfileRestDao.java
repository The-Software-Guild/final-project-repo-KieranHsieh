package com.sg.song_rec.data.rest;

import com.sg.song_rec.data.UserProfileDao;
import com.sg.song_rec.entities.application.User;
import com.sg.song_rec.entities.application.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

/**
 * An implementation of RestDao that uses
 * REST calls to retrieve data
 */
@Repository
public class UserProfileRestDao extends RestDao implements UserProfileDao {

    /**
     * The base endpoint used
     */
    public static final String ENDPOINT = "https://api.spotify.com/v1/me";

    /**
     * Constructs a new UserProfileRestDao
     * @param template The RestTemplate used when making requests
     */
    @Autowired
    public UserProfileRestDao(RestTemplate template) {
        super(template);
    }

    /**
     * @param user The user whose profile to get
     * @return The user's profile
     */
    @Override
    public UserProfile getUserProfile(User user) {
        return queryEndpoint(user, ENDPOINT, UserProfile.class);
    }
}
