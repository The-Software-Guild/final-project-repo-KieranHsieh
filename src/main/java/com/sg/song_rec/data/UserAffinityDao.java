package com.sg.song_rec.data;

import com.sg.song_rec.entities.application.AffinityTimeRange;
import com.sg.song_rec.entities.application.Artist;
import com.sg.song_rec.entities.application.Track;
import com.sg.song_rec.entities.application.User;

import java.util.List;

/**
 * An interface for extracting information
 * about a user's most listened to artists and tracks
 */
public interface UserAffinityDao {
    /**
     * Gets the user's most listened to artists in a given time range
     * @param user The user to retrieve information from
     * @param limit The number of artists to retrieve
     * @param range The time range used to calculate the most listened to artists
     * @return A list of artists
     */
    List<Artist> getTopArtists(User user, int limit, AffinityTimeRange range);

    /**
     * Gets the user's most listened to tracks in a given time range
     * @param user The user to retrieve information from
     * @param limit The number of tracks to retrieve
     * @param range The time range used to calculate the most listened to tracks
     * @return A list of tracks
     */
    List<Track> getTopTracks(User user, int limit, AffinityTimeRange range);
}
