package com.sg.song_rec.util.mappers;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * A utility class used to convert a map of keys and values
 * to a x/www-url-form-encoded String
 */
public class UrlFormBuilder {
    private HashMap<String, String> values = new HashMap<>();

    /**
     * Sets a key/value pair in the outputted string
     * @param key The key
     * @param value The value
     * @return The calling UrlFormBuilder
     */
    public UrlFormBuilder set(String key, String value) {
        values.put(key, value);
        return this;
    }

    /**
     * Finishes the build process and converts the builder's
     * internal key/value map into an encoded string
     * @return The encoded String
     */
    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner("&");
        for(Map.Entry<String, String> entry : values.entrySet()) {
            joiner.add(entry.getKey() + "=" + entry.getValue());
        }
        return joiner.toString();
    }
}
