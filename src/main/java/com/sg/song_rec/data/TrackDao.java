package com.sg.song_rec.data;

import com.sg.song_rec.entities.application.Track;
import com.sg.song_rec.entities.application.User;

import java.util.List;

public interface TrackDao {
    Track getTrack(User user, String id);
    List<Track> getTracks(User user, List<String> ids);
}
