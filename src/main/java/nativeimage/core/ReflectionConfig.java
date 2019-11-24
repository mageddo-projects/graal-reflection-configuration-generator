package nativeimage.core;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ReflectionConfig {
	private Class clazz;
	private boolean allDeclaredConstructors;
	private boolean allPublicConstructors;
	private boolean allDeclaredMethods;
	private boolean allPublicMethods;
	private boolean allPublicFields;
	private boolean allDeclaredFields;
}
