package com.sg.song_rec.service;

import com.sg.song_rec.entities.application.Track;
import com.sg.song_rec.entities.application.User;
import com.sg.song_rec.entities.application.UserProfile;

import java.util.List;

public interface RecommendationService {
    List<Track> getRecommendations(User user);
    UserProfile getUserProfile(User user);
}
