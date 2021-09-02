package com.sg.song_rec.service;

import com.sg.song_rec.data.*;
import com.sg.song_rec.entities.application.*;
import com.sg.song_rec.util.SRMath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class DefaultRecommendationService implements RecommendationService {
    private RecommendationDao recommendationDao;
    private UserAffinityDao affinityDao;
    private AudioFeaturesDao audioFeaturesDao;
    private UserProfileDao userProfileDao;

    private static final double ACOUSTIC_NOISE = 0.2;
    private static final double DANCEABILITY_NOISE = 0.2;
    private static final double ENERGY_NOISE = 0.2;
    private static final double INSTRUMENTALNESS_NOISE = 0.2;
    private static final double LIVENESS_NOSIE = 0.0;
    private static final double LOUDNESS_NOSIE = 5.0;
    private static final double SPEECHINESS_NOISE = 0.05;
    private static final double TEMPO_NOISE = 20;


    @Autowired
    public DefaultRecommendationService(RecommendationDao recommendationDao, UserAffinityDao affinityDao, AudioFeaturesDao audioFeaturesDao, UserProfileDao userProfileDao) {
        this.recommendationDao = recommendationDao;
        this.affinityDao = affinityDao;
        this.audioFeaturesDao = audioFeaturesDao;
        this.userProfileDao = userProfileDao;

    }

    @Override
    public List<Track> getRecommendations(User user) {
        List<Artist> topArtists = affinityDao.getTopArtists(user, 5, AffinityTimeRange.SHORT_TERM);
        List<Track> topTracks = affinityDao.getTopTracks(user, 20, AffinityTimeRange.MEDIUM_TERM);
        List<AudioFeatures> features = audioFeaturesDao.getAudioFeaturesForTracks(user, topTracks);
        AudioFeatures averageFeatures = getAverageFeatures(features);
        return recommendationDao.getRecommendations(user, 10,
                topArtists,
                topTracks,
                topArtists.get(0).getGenres(),
                averageFeatures);
    }

    @Override
    public UserProfile getUserProfile(User user) {
        return userProfileDao.getUserProfile(user);
    }

    private AudioFeatures getAverageFeatures(List<AudioFeatures> features) {
        AudioFeatures avgFeats = new AudioFeatures();
        // Check if size is 0 here to avoid divide by 0 errors
        if(features.size() == 0) {
            return avgFeats;
        }
        // Sum features to be averaged in the next step
        for(AudioFeatures f : features) {
            avgFeats.setAcousticness(		f.getAcousticness() + avgFeats.getAcousticness());
            avgFeats.setDanceability(		f.getDanceability() + avgFeats.getDanceability());
            avgFeats.setEnergy(				f.getEnergy() + avgFeats.getEnergy());
            avgFeats.setInstrumentalness(	f.getInstrumentalness() + avgFeats.getInstrumentalness());
            avgFeats.setKey(				f.getKey() + avgFeats.getKey());
            avgFeats.setLiveness(			f.getLiveness() + avgFeats.getLiveness());
            avgFeats.setLoudness(			f.getLoudness() + avgFeats.getLoudness());
            avgFeats.setMode(				f.getMode() + avgFeats.getMode());
            avgFeats.setSpeechiness(		f.getSpeechiness() + avgFeats.getSpeechiness());
            avgFeats.setTempo(				f.getTempo() + avgFeats.getTempo());
            avgFeats.setValence(			f.getValence() + avgFeats.getValence());
        }
        Random random = new Random();

        // Average each audio feature and offset with a random value
        avgFeats.setAcousticness(
                SRMath.clamp(
                        randomDoubleBetween(random, ACOUSTIC_NOISE, -ACOUSTIC_NOISE) + (avgFeats.getAcousticness() / features.size()),
                        0.0, 1.0));
        avgFeats.setDanceability(
                SRMath.clamp(
                        randomDoubleBetween(random, DANCEABILITY_NOISE, -DANCEABILITY_NOISE) + (avgFeats.getDanceability() / features.size()),
                        0.0, 1.0));
        avgFeats.setEnergy(
                SRMath.clamp(
                        randomDoubleBetween(random, ENERGY_NOISE, -ENERGY_NOISE) + (avgFeats.getEnergy() / features.size()),
                        0.0, 1.0));
        avgFeats.setInstrumentalness(
                SRMath.clamp(
                        randomDoubleBetween(random, INSTRUMENTALNESS_NOISE, - INSTRUMENTALNESS_NOISE) + (avgFeats.getInstrumentalness() / features.size()),
                        0.0, 1.0));
        avgFeats.setLiveness(
                SRMath.clamp(
                    randomDoubleBetween(random, LIVENESS_NOSIE, -LIVENESS_NOSIE) + (avgFeats.getLiveness() / features.size()),
                    0.0, 1.0));
        avgFeats.setLoudness(
                SRMath.clamp(
                        randomDoubleBetween(random, LOUDNESS_NOSIE, -LOUDNESS_NOSIE) + (avgFeats.getLoudness() / features.size()),
                        -100.0, 100.0));
        avgFeats.setSpeechiness(
                SRMath.clamp(randomDoubleBetween(random, SPEECHINESS_NOISE, -SPEECHINESS_NOISE) + (avgFeats.getSpeechiness() / features.size()),
                        0.0, 1.0));
        avgFeats.setTempo(
                SRMath.clamp(
                        randomDoubleBetween(random, TEMPO_NOISE, -TEMPO_NOISE) + avgFeats.getTempo() / features.size(),
                        0.0, 400.0));

        // Features that are not offset
        avgFeats.setKey(				((avgFeats.getKey() / features.size())));
        avgFeats.setMode(				((avgFeats.getMode() / features.size())));
        avgFeats.setValence(			((avgFeats.getValence() / features.size())));
        return avgFeats;
    }

    private double randomDoubleBetween(Random obj, double low, double high) {
        return (obj.nextDouble() * (high - low)) + low;
    }
}
