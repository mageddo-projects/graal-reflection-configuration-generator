package nativeimage;

import nativeimage.core.ReflectionConfig;

import javax.tools.JavaFileObject;

public class ReflectionConfigAppenderAnnotationProcessing implements ReflectionConfigAppender {

	private final JavaFileObject fileObject;

	public ReflectionConfigAppenderAnnotationProcessing(JavaFileObject fileObject) {
		this.fileObject = fileObject;
	}

	@Override
	public void append(ReflectionConfig reflectionConfig) {
		throw new UnsupportedOperationException();
	}
}
