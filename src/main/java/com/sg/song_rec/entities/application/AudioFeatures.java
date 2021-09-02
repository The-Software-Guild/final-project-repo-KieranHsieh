package com.sg.song_rec.entities.application;

import java.util.Objects;

public class AudioFeatures {
    private double danceability;
    private double energy;
    private int key;
    private double loudness;
    private int mode;
    private double speechiness;
    private double acousticness;
    private double instrumentalness;
    private double liveness;
    private double valence;
    private double tempo;

    /**
     * Gets the danceability
     *
     * @return double The danceability
     */
    public double getDanceability() {
        return danceability;
    }

    /**
     * Sets the danceability
     *
     * @param danceability The danceability to set
     */
    public void setDanceability(double danceability) {
        this.danceability = danceability;
    }

    /**
     * Gets the energy
     *
     * @return double The energy
     */
    public double getEnergy() {
        return energy;
    }

    /**
     * Sets the energy
     *
     * @param energy The energy to set
     */
    public void setEnergy(double energy) {
        this.energy = energy;
    }

    /**
     * Gets the key
     *
     * @return int The key
     */
    public int getKey() {
        return key;
    }

    /**
     * Sets the key
     *
     * @param key The key to set
     */
    public void setKey(int key) {
        this.key = key;
    }

    /**
     * Gets the loudness
     *
     * @return double The loudness
     */
    public double getLoudness() {
        return loudness;
    }

    /**
     * Sets the loudness
     *
     * @param loudness The loudness to set
     */
    public void setLoudness(double loudness) {
        this.loudness = loudness;
    }

    /**
     * Gets the mode
     *
     * @return int The mode
     */
    public int getMode() {
        return mode;
    }

    /**
     * Sets the mode
     *
     * @param mode The mode to set
     */
    public void setMode(int mode) {
        this.mode = mode;
    }

    /**
     * Gets the speechiness
     *
     * @return double The speechiness
     */
    public double getSpeechiness() {
        return speechiness;
    }

    /**
     * Sets the speechiness
     *
     * @param speechiness The speechiness to set
     */
    public void setSpeechiness(double speechiness) {
        this.speechiness = speechiness;
    }

    /**
     * Gets the acousticness
     *
     * @return double The acousticness
     */
    public double getAcousticness() {
        return acousticness;
    }

    /**
     * Sets the acousticness
     *
     * @param acousticness The acousticness to set
     */
    public void setAcousticness(double acousticness) {
        this.acousticness = acousticness;
    }

    /**
     * Gets the instrumentalness
     *
     * @return double The instrumentalness
     */
    public double getInstrumentalness() {
        return instrumentalness;
    }

    /**
     * Sets the instrumentalness
     *
     * @param instrumentalness The instrumentalness to set
     */
    public void setInstrumentalness(double instrumentalness) {
        this.instrumentalness = instrumentalness;
    }

    /**
     * Gets the liveness
     *
     * @return double The liveness
     */
    public double getLiveness() {
        return liveness;
    }

    /**
     * Sets the liveness
     *
     * @param liveness The liveness to set
     */
    public void setLiveness(double liveness) {
        this.liveness = liveness;
    }

    /**
     * Gets the valence
     *
     * @return double The valence
     */
    public double getValence() {
        return valence;
    }

    /**
     * Sets the valence
     *
     * @param valence The valence to set
     */
    public void setValence(double valence) {
        this.valence = valence;
    }

    /**
     * Gets the tempo
     *
     * @return double The tempo
     */
    public double getTempo() {
        return tempo;
    }

    /**
     * Sets the tempo
     *
     * @param tempo The tempo to set
     */
    public void setTempo(double tempo) {
        this.tempo = tempo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AudioFeatures features = (AudioFeatures) o;
        return Double.compare(features.getDanceability(), getDanceability()) == 0 &&
                Double.compare(features.getEnergy(), getEnergy()) == 0 &&
                getKey() == features.getKey() &&
                Double.compare(features.getLoudness(), getLoudness()) == 0 &&
                getMode() == features.getMode() &&
                Double.compare(features.getSpeechiness(), getSpeechiness()) == 0 &&
                Double.compare(features.getAcousticness(), getAcousticness()) == 0 &&
                Double.compare(features.getInstrumentalness(), getInstrumentalness()) == 0 &&
                Double.compare(features.getLiveness(), getLiveness()) == 0 &&
                Double.compare(features.getValence(), getValence()) == 0 &&
                Double.compare(features.getTempo(), getTempo()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDanceability(), getEnergy(), getKey(), getLoudness(), getMode(), getSpeechiness(), getAcousticness(), getInstrumentalness(), getLiveness(), getValence(), getTempo());
    }
}
