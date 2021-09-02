package com.sg.song_rec.data;

import com.sg.song_rec.entities.application.Artist;
import com.sg.song_rec.entities.application.AudioFeatures;
import com.sg.song_rec.entities.application.Track;
import com.sg.song_rec.entities.application.User;

import java.util.List;

public interface RecommendationDao {
    List<Track> getRecommendations(User user, int limit, List<Artist> artists, List<Track> tracks, List<String> genres, AudioFeatures targetFeatures);
}
