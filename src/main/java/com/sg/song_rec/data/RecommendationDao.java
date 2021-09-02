package com.sg.song_rec.data;

import com.sg.song_rec.entities.application.Artist;
import com.sg.song_rec.entities.application.AudioFeatures;
import com.sg.song_rec.entities.application.Track;
import com.sg.song_rec.entities.application.User;

import java.util.List;

/**
 * An interface for retrieving song recommendations
 */
public interface RecommendationDao {
    /**
     * Gets song recommendations for a given user using the provided values as parameters
     * for the recommendation retrieval
     * @param user The user used to authenticate requests for recommendations
     * @param limit The number of recommendations to retrieve
     * @param artists A list of artists used when retrieving recommendations. The artists
     *                provided in the parameter are NOT guaranteed to all be used in the final
     *                request
     * @param tracks A list of tracks used when retrieving recommendations. The tracks
     *               provided in the parameter are NOT guaranteed to all be used in the final
     *               request
     * @param genres A list of genres used when retrieving recommendations. The genres provided
     *               in the parameter are NOT guaranteed to all be used in the final request
     * @param targetFeatures An AudioFeatures object with values representing target audio feature values
     *                       for the retrieved recommendations
     * @return A list of tracks with a length of limit representing the retrieved recommendations
     */
    List<Track> getRecommendations(User user, int limit, List<Artist> artists, List<Track> tracks, List<String> genres, AudioFeatures targetFeatures);
}
