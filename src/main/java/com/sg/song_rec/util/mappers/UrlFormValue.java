package com.sg.song_rec.util.mappers;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * An annotation used to denote
 * fields that are mappable to x/www-url-form-encoded values
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface UrlFormValue {
    String value() default "";
}
