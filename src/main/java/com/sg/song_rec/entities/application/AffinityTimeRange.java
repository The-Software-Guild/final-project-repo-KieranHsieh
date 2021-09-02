package com.sg.song_rec.entities.application;

/**
 * A utility class representing
 * a short, medium, or long term time range
 */
public class AffinityTimeRange {
    public static final AffinityTimeRange SHORT_TERM = new AffinityTimeRange("short_term");
    public static final AffinityTimeRange MEDIUM_TERM = new AffinityTimeRange("medium_term");
    public static final AffinityTimeRange LONG_TERM = new AffinityTimeRange("long_term");
    /**
     * An internal value representing the time range
     */
    private String value;

    /**
     * Constructs a new AffinityTimeRange with a given value
     * @param value The value of the time range
     */
    private AffinityTimeRange(String value) {
        this.value = value;
    }

    /**
     * Converts an AffinityTimeRange to a string
     * @return The affinity time range as a string
     */
    @Override
    public String toString() {
        return value;
    }
}
