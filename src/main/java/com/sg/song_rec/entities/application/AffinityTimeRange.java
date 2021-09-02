package com.sg.song_rec.entities.application;

public class AffinityTimeRange {
    public static final AffinityTimeRange SHORT_TERM = new AffinityTimeRange("short_term");
    public static final AffinityTimeRange MEDIUM_TERM = new AffinityTimeRange("medium_term");
    public static final AffinityTimeRange LONG_TERM = new AffinityTimeRange("long_term");
    private String value;
    private AffinityTimeRange(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
