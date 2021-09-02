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

/**
 * A monostate class used as a utility
 * for extracting reflected data about a given class
 */
public class ClassReflector {
    private ClassReflector() {}

    /**
     * Gets methods annotated with a specified annotation in a target class
     * @param builder The builder containing the parameters for the reflector
     * @param <A> The annotation type
     * @return A List ReflectionResults containing the found methods and their associated annotation instances
     */
    public static <A extends Annotation> List<ReflectionResult<Method, A>> getAnnotatedMethods(ReflectionQueryBuilder<A> builder) {
        ArrayList<ReflectionResult<Method, A>> validMethods = new ArrayList<>();
        return _getAnnotatedElements(builder.getTarget(),
                builder.getTargetAnnotation(),
                validMethods,
                Class::getDeclaredMethods,
                builder.isRecursive());
    }

    /**
     * Gets fields annotated with a specified annotation in a target class
     * @param builder The builder containing the parameters for the reflector
     * @param <A> The annotation type
     * @return A List ReflectionResults containing the found fields and their associated annotation instances
     */
    public static <A extends Annotation> List<ReflectionResult<Field, A>> getAnnotatedFields(ReflectionQueryBuilder<A> builder) {
        ArrayList<ReflectionResult<Field, A>> validFields = new ArrayList<>();
        return _getAnnotatedElements(builder.getTarget(),
                builder.getTargetAnnotation(),
                validFields,
                Class::getDeclaredFields,
                builder.isRecursive());
    }

    /**
     * Get a method for a field that are publicly accessible and prefixed by a value.
     * e.g. setValue or getValue are prefixed by "set" and "get" respectively
     * @param prefix The prefix used when matching method names
     * @param target The target field to get fields for
     * @return The first Method that matches the provided conditions
     */
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

    /**
     * A recursive helper method used to extract annotated elements from a class
     * @param target The class to extract annotated elements from
     * @param annotation The annotation type that is looked for
     * @param valid A list of valid reflection results that are eventually returned
     * @param extractFunc The function used to extract AnnotatedElements from target
     * @param recursive Whether or not to recursively search parent classes for annotated elements
     * @param <T> A type that extends AnnotatedElement
     * @param <A> A type that extends Annotation
     * @return A list of reflection results containing the annotation instance and annotated element
     */
    private static <T extends AnnotatedElement, A extends Annotation> List<ReflectionResult<T, A>> _getAnnotatedElements(Class<?> target,
                                                                           Class<A> annotation,
                                                                           List<ReflectionResult<T, A>> valid,
                                                                           Function<Class<?>, T[]> extractFunc,
                                                                           boolean recursive) {
        // Recurse superclasses if recursive option is on
        Class<?> superClass = target.getSuperclass();
        if(superClass != null && recursive) {
            _getAnnotatedElements(superClass, annotation, valid, extractFunc, recursive);
        }
        // Get annotated elements
        T[] elements = extractFunc.apply(target);
        for(T e : elements) {
            // Get annotations that match the requested type
            A[] elementAnnotations = e.getDeclaredAnnotationsByType(annotation);
            if(elementAnnotations != null && elementAnnotations.length > 0) {
                ReflectionResult<T, A> result = new ReflectionResult<>(e, elementAnnotations);
                valid.add(result);
            }
        }
        return valid;
    }
}
