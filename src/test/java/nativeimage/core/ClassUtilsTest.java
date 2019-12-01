package nativeimage.core;

import com.mageddo.aptools.ClassUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClassUtilsTest {

	@Test
	void mustFindClassPackage(){
		// arrange
		final String className = Map.class.getName();

		// act
		final String classPackage = ClassUtils.getClassPackage(className);

		// assert
		assertEquals("java.util", classPackage);
	}
}
