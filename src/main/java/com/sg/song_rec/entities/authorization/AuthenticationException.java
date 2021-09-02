package com.sg.song_rec.entities.authorization;

import java.io.IOException;

public class AuthenticationException extends IOException {
    public AuthenticationException(String msg) {
        super(msg);
    }
}
