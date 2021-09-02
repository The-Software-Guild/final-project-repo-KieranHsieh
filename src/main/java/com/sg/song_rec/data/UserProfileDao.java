package com.sg.song_rec.data;

import com.sg.song_rec.entities.application.User;
import com.sg.song_rec.entities.application.UserProfile;

/**
 * An interface for extracting user profile information
 */
public interface UserProfileDao {
    /**
     * Gets the profile of a given user
     * @param user The user whose profile to get
     * @return The user's profile
     */
    UserProfile getUserProfile(User user);
}
