package com.mageddo.aptools;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import java.util.Set;

public interface Processor {
	/**
	 * Handle annotation process event and construct
	 */
	void process(Set<TypeElement> annotations, RoundEnvironment roundEnv);
}
