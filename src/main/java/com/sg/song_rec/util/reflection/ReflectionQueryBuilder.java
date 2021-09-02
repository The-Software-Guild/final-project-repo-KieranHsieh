package com.sg.song_rec.util.reflection;

import java.lang.annotation.Annotation;

/**
 * A utility class used to build out parameters in a ClassReflection query method call
 * @param <A> The annotation type
 */
public class ReflectionQueryBuilder<A extends Annotation> {
    private boolean recursive = false;
    private Class<?> target = null;
    private Class<A> targetAnnotation;

    public ReflectionQueryBuilder() {

    }

    /**
     * Gets the recursive
     *
     * @return boolean The recursive
     */
    public boolean isRecursive() {
        return recursive;
    }

    /**
     * Sets whether or not the target class is recursively searched
     * @param recursive Whether or not to recursively search the target class for annotations
     * @return The calling ReflectionQueryBuilder
     */
    public ReflectionQueryBuilder<A> setRecursive(boolean recursive) {
        this.recursive = recursive;
        return this;
    }

    /**
     * Gets the target
     *
     * @return java.lang.Class<?> The target
     */
    public Class<?> getTarget() {
        return target;
    }

    /**
     * Sets the target for the query
     * @param target The target of the query
     * @return The calling ReflectionQueryBuilder
     */
    public ReflectionQueryBuilder<A> setTarget(Class<?> target) {
        this.target = target;
        return this;
    }

    /**
     * Gets the targetAnnotation
     *
     * @return java.lang.Class<A> The targetAnnotation
     */
    public Class<A> getTargetAnnotation() {
        return targetAnnotation;
    }

    /**
     * Sets the annotation target of the query
     * @param targetAnnotation The target annotation
     * @return The calling ReflectionQueryBuilder
     */
    public ReflectionQueryBuilder<A> setTargetAnnotation(Class<A> targetAnnotation) {
        this.targetAnnotation = targetAnnotation;
        return this;
    }
}
