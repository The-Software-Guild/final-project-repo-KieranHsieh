package com.sg.song_rec.util.mappers;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class UrlFormBuilder {
    private HashMap<String, String> values = new HashMap<>();

    public UrlFormBuilder set(String key, String value) {
        values.put(key, value);
        return this;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner("&");
        for(Map.Entry<String, String> entry : values.entrySet()) {
            joiner.add(entry.getKey() + "=" + entry.getValue());
        }
        return joiner.toString();
    }
}
