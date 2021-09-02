package com.sg.song_rec.data;

import com.sg.song_rec.entities.application.Track;
import com.sg.song_rec.entities.application.User;

import java.util.List;

/**
 * An interface for retrieving tracks
 */
public interface TrackDao {
    /**
     * Retrieves a single track given its id
     * @param user The user used to authenticate requests for tracks
     * @param id The ID of the track to retrieve
     * @return The retrieved track if it exists, or null if it does not
     */
    Track getTrack(User user, String id);

    /**
     * Retrieves multiple tracks given their ids
     * @param user The user used to authenticate requests for tracks
     * @param ids The list of IDs of the tracks to retrieve
     * @return The retrieved tracks
     */
    List<Track> getTracks(User user, List<String> ids);
}
