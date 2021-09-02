package com.sg.song_rec.util.mappers;

import com.fasterxml.jackson.databind.util.LRUMap;
import com.sg.song_rec.util.reflection.ClassReflector;
import com.sg.song_rec.util.reflection.ObjectMapperParseException;
import com.sg.song_rec.util.reflection.ReflectionQueryBuilder;
import com.sg.song_rec.util.reflection.ReflectionResult;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Pattern;

public class ObjectMapper {
    public String writeValueToString(Object o) throws ObjectMapperParseException {
        // Reflect object and get it's form value fields
        ReflectionQueryBuilder<UrlFormValue> builder = new ReflectionQueryBuilder<UrlFormValue>()
                .setTarget(o.getClass())
                .setTargetAnnotation(UrlFormValue.class);
        List<ReflectionResult<Field, UrlFormValue>> fields = ClassReflector.getAnnotatedFields(builder);

        // Build output string
        UrlFormBuilder formBuilder = new UrlFormBuilder();
        for(ReflectionResult<Field, UrlFormValue> r : fields) {
            // Decide between using the annotation value or the field name
            String formKey = r.getAnnotation()[0].value();
            if(formKey.isEmpty()) {
                formKey = r.getElement().getName();
            }

            // Attempt to find an accessor so we can get the value of the field
            Method accessor = ClassReflector.getPublicPrefixedMethod("get", r.getElement());
            if(accessor == null) {
                throw new ObjectMapperParseException("Object Mapper Parse Error: Failed to find appropriate accessor for " + r.getElement().getName());
            }
            try {
                // Attempt to append the accessed value to the form builder
                Object obj = accessor.invoke(o);
                formBuilder.set(formKey, obj.toString());
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new ObjectMapperParseException(e.getMessage());
            }
        }
        return formBuilder.toString();
    }
}
