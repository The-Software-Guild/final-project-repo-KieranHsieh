package com.sg.song_rec.data;

import com.sg.song_rec.entities.application.AudioFeatures;
import com.sg.song_rec.entities.application.Track;
import com.sg.song_rec.entities.application.User;

import java.util.List;

/**
 * An interface for retrieving audio features from a track
 */
public interface AudioFeaturesDao {
    /**
     * Gets the audio features associated with a given track
     * @param user The user used to authenticate requests for audio features
     * @param track The track to get audio features for
     * @return The Audio features for the track
     */
    AudioFeatures getAudioFeaturesForTrack(User user, Track track);

    /**
     * Gets a list of audio features associated with multiple tracks
     * @param user The user used to authenticate requests for audio features
     * @param tracks The list of tracks to get audio features for
     * @return The audio features retrieved for each track.
     */
    List<AudioFeatures> getAudioFeaturesForTracks(User user, List<Track> tracks);
}
