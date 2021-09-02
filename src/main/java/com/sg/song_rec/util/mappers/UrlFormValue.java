package com.sg.song_rec.util.mappers;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface UrlFormValue {
    String value() default "";
}
