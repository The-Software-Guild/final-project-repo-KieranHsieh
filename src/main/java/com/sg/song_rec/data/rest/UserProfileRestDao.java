package com.sg.song_rec.data.rest;

import com.sg.song_rec.data.UserProfileDao;
import com.sg.song_rec.entities.application.User;
import com.sg.song_rec.entities.application.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class UserProfileRestDao extends RestDao implements UserProfileDao {

    public static final String ENDPOINT = "https://api.spotify.com/v1/me";

    @Autowired
    public UserProfileRestDao(RestTemplate template) {
        super(template);
    }

    @Override
    public UserProfile getUserProfile(User user) {
        return queryEndpoint(user, ENDPOINT, UserProfile.class);
    }
}
