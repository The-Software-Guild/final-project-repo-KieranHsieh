package com.sg.song_rec.data;

import com.sg.song_rec.entities.application.AudioFeatures;
import com.sg.song_rec.entities.application.Track;
import com.sg.song_rec.entities.application.User;

import java.util.List;

public interface AudioFeaturesDao {
    AudioFeatures getAudioFeaturesForTrack(User user, Track track);
    List<AudioFeatures> getAudioFeaturesForTracks(User user, List<Track> tracks);
}
