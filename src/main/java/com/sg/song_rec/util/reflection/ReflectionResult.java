package com.sg.song_rec.util.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

/**
 * A data structure containing an annotated element and its associated annotation instance
 * @param <T> The annotated element type
 * @param <A> The annotation type
 */
public class ReflectionResult<T extends AnnotatedElement, A extends Annotation> {
    private T element;
    private A[] annotation;

    public ReflectionResult() {
    }

    /**
     * Constructs a new reflection result
     * @param element The AnnotatedElement
     * @param annotation The annotation instances
     */
    public ReflectionResult(T element, A[] annotation) {
        this.element = element;
        this.annotation = annotation;
    }

    /**
     * Gets the element
     *
     * @return T The element
     */
    public T getElement() {
        return element;
    }

    /**
     * Sets the element
     *
     * @param element The element to set
     */
    public void setElement(T element) {
        this.element = element;
    }

    /**
     * Gets the annotation
     *
     * @return A[] The annotation
     */
    public A[] getAnnotation() {
        return annotation;
    }

    /**
     * Sets the annotation
     *
     * @param annotation The annotation to set
     */
    public void setAnnotation(A[] annotation) {
        this.annotation = annotation;
    }
}
