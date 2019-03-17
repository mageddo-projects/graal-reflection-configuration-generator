package com.mageddo.graal.reflection.configuration;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RuntimeReflection {

	boolean allDeclaredConstructors() default false;

	boolean allPublicConstructors() default false;

	boolean allPublicFields() default false;

	boolean allPublicMethods() default false;

	boolean allDeclaredMethods() default false;

	boolean allDeclaredFields() default false;

}
