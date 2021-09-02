package com.sg.song_rec.data;

import com.sg.song_rec.entities.application.User;
import com.sg.song_rec.entities.application.UserProfile;

public interface UserProfileDao {
    UserProfile getUserProfile(User user);
}
