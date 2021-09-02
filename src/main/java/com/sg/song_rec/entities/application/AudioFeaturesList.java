package com.sg.song_rec.entities.application;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * A utility class used when de-serializing
 * a JSON array of AudioFeatures objects
 */
public class AudioFeaturesList {
    @JsonProperty("audio_features")
    private List<AudioFeatures> audioFeatures;

    /**
     * Gets the audioFeatures
     *
     * @return java.util.List<com.sg.song_rec.entities.application.AudioFeatures> The audioFeatures
     */
    public List<AudioFeatures> getAudioFeatures() {
        return audioFeatures;
    }

    /**
     * Sets the audioFeatures
     *
     * @param audioFeatures The audioFeatures to set
     */
    public void setAudioFeatures(List<AudioFeatures> audioFeatures) {
        this.audioFeatures = audioFeatures;
    }
}
