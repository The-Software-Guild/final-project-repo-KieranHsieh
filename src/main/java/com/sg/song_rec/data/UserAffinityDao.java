package com.sg.song_rec.data;

import com.sg.song_rec.entities.application.AffinityTimeRange;
import com.sg.song_rec.entities.application.Artist;
import com.sg.song_rec.entities.application.Track;
import com.sg.song_rec.entities.application.User;

import java.util.List;

public interface UserAffinityDao {
    List<Artist> getTopArtists(User user, int limit, AffinityTimeRange range);
    List<Track> getTopTracks(User user, int limit, AffinityTimeRange range);
}
