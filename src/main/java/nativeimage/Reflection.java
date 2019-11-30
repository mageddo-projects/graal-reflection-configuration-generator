package nativeimage;

import java.lang.annotation.*;

/**
 * Register the annotated element to be scanned and generate a reflection config file,
 * instead of the current annotated element you can specify
 * {@link #scanPackage()}, {@link #scanClass()} or {@link #scanClassName()} instead
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.PACKAGE})
//@Repeatable(value = RuntimeReflections.class)
public @interface Reflection {

	/**
	 * The package to be scanned to generate reflection config, e.g <code>java.lang</code>
	 */
	String scanPackage() default "";

	/**
	 * The class to be scanned to generate reflection config
	 */
	Class scanClass() default Void.class;

	/**
	 * The class name to be scanned to generate reflection config, e.g <code>java.lang.String</code>
	 */
	String scanClassName() default "";

	boolean declaredConstructors() default false;

	boolean publicConstructors() default false;

	boolean declaredMethods() default false;

	boolean publicMethods() default false;

	boolean publicFields() default false;

	boolean declaredFields() default false;

}
