package com.sg.song_rec.util;

/**
 * A static class used for simple math functions
 * not provided in the default java math library
 */
public class SRMath {
    private SRMath() {}

    /**
     * Clamps a value between a minimum and maximum
     * @param val The value to clamp
     * @param min The minimum value
     * @param max The maximum value
     * @param <T> THe type of the value
     * @return The clamped value
     */
    public static <T extends Comparable<T>> T clamp(T val, T min, T max) {
        if (val.compareTo(min) < 0) {
            return min;
        }
        else if (val.compareTo(max) > 0) {
            return max;
        }
        else return val;
    }
}
