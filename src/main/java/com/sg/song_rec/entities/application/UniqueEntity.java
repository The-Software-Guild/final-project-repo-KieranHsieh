package com.sg.song_rec.entities.application;

public abstract class UniqueEntity {
    private String id;

    /**
     * Gets the id
     *
     * @return java.lang.String The id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id
     *
     * @param id The id to set
     */
    public void setId(String id) {
        this.id = id;
    }
}
