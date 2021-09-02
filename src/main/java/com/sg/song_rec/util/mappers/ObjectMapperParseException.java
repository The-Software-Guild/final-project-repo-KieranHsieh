package com.sg.song_rec.util.mappers;

/**
 * An exception thrown when an object mapper could not parse an object
 */
public class ObjectMapperParseException extends Exception{
    /**
     * Constructs a new ObjectMapperParseException
     * @param msg The message of the exception
     */
    public ObjectMapperParseException(String msg) {
        super(msg);
    }
}
