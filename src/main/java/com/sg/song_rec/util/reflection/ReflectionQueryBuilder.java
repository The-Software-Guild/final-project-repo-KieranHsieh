package com.sg.song_rec.util.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

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

    public ReflectionQueryBuilder<A> setTargetAnnotation(Class<A> targetAnnotation) {
        this.targetAnnotation = targetAnnotation;
        return this;
    }
}
