package com.sg.song_rec.service;

import com.sg.song_rec.entities.application.Track;
import com.sg.song_rec.entities.application.User;
import com.sg.song_rec.entities.application.UserProfile;

import java.util.List;

/**
 * An interface used for getting recommendations and user profile information
 */
public interface RecommendationService {
    /**
     * Gets recommendations for a given user
     * @param user The user to get recommendations for
     * @return The recommendations
     */
    List<Track> getRecommendations(User user);

    /**
     * Gets the profile of a given user
     * @param user The user to get the profile of
     * @return The profile of the user
     */
    UserProfile getUserProfile(User user);
}
