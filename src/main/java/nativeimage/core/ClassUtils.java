package nativeimage.core;

public class ClassUtils {
	public static String getClassPackage(String className) {
		final int lastIndexOf = className.lastIndexOf('.');
		if(lastIndexOf < 0){
			return className;
		}
		return className.substring(0, lastIndexOf);
	}
}
