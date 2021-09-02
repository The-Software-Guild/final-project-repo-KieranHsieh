package com.sg.song_rec.util.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClassReflector {
    private ClassReflector() {}

    public static <A extends Annotation> List<ReflectionResult<Method, A>> getAnnotatedMethods(ReflectionQueryBuilder<A> builder) {
        ArrayList<ReflectionResult<Method, A>> validMethods = new ArrayList<>();
        return _getAnnotatedElements(builder.getTarget(),
                builder.getTargetAnnotation(),
                validMethods,
                Class::getDeclaredMethods,
                builder.isRecursive());
    }
    public static <A extends Annotation> List<ReflectionResult<Field, A>> getAnnotatedFields(ReflectionQueryBuilder<A> builder) {
        ArrayList<ReflectionResult<Field, A>> validFields = new ArrayList<>();
        return _getAnnotatedElements(builder.getTarget(),
                builder.getTargetAnnotation(),
                validFields,
                Class::getDeclaredFields,
                builder.isRecursive());
    }
    public static Method getPublicPrefixedMethod(String prefix, Field target) {
        Class<?> parent = target.getDeclaringClass();
        Method[] methods = parent.getMethods();
        for(Method m : methods) {
            if(Modifier.isPublic(m.getModifiers())) {
                if(m.getName().startsWith(prefix) && m.getName().toLowerCase().endsWith(target.getName().toLowerCase())) {
                    return m;
                }
            }
        }
        return null;
    }
    private static <T extends AnnotatedElement, A extends Annotation> List<ReflectionResult<T, A>> _getAnnotatedElements(Class<?> target,
                                                                           Class<A> annotation,
                                                                           List<ReflectionResult<T, A>> valid,
                                                                           Function<Class<?>, T[]> extractFunc,
                                                                           boolean recursive) {
        Class<?> superClass = target.getSuperclass();
        if(superClass != null && recursive) {
            _getAnnotatedElements(superClass, annotation, valid, extractFunc, recursive);
        }
        T[] elements = extractFunc.apply(target);
        for(T e : elements) {
            A[] elementAnnotations = e.getDeclaredAnnotationsByType(annotation);
            if(elementAnnotations != null && elementAnnotations.length > 0) {
                ReflectionResult<T, A> result = new ReflectionResult<>(e, elementAnnotations);
                valid.add(result);
            }
        }
        return valid;
    }
}
