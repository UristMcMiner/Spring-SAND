package de.dhbw_mannheim.sand.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 
 * Annotation definition for the general Aspect to use for duplication.
 *
 */
@Target( { TYPE, METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Prototype{
}
