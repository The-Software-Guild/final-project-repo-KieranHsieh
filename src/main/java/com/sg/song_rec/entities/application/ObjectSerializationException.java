package com.sg.song_rec.entities.application;

/**
 * An exception thrown when an object fails to be serialized
 */
public class ObjectSerializationException extends Exception {
    /**
     * Constructs a new ObjectSerializationException
     * @param msg The message of the exception
     */
    public ObjectSerializationException(String msg) {
        super(msg);
    }
}
