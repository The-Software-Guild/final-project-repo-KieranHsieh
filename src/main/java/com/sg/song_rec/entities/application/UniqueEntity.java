package com.sg.song_rec.entities.application;

/**
 * An abstract class representing
 * an entity defined by a unique String ID
 */
public abstract class UniqueEntity {
    /**
     * The ID of the entity
     */
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
