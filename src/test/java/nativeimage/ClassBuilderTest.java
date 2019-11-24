package nativeimage;

import nativeimage.vo.Pojo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sun.reflect.annotation.AnnotationParser;

import javax.lang.model.element.Element;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

class ClassBuilderTest {

	@Test
	void mustMapScanClassName() {
		// arrange

		final Map<String, Object> params = new LinkedHashMap<>();
		params.put("scanClassName", Pojo.class.getName());
		params.put("scanClass", Set.class);

		final RuntimeReflection ann = (RuntimeReflection) AnnotationParser.annotationForMap(
			RuntimeReflection.class, params
		);

		// act
		final Set<Class> classes = ClassBuilder.of(null, ann);

		// assert
		assertEquals(1, classes.size());
		assertEquals(Pojo.class, classes.iterator().next());
	}

	@Test
	void mustMapScanClass() {
		// arrange

		final Map<String, Object> params = new LinkedHashMap<>();
		params.put("scanClassName", "");
		params.put("scanClass", Pojo.class);

		final RuntimeReflection ann = (RuntimeReflection) AnnotationParser.annotationForMap(
			RuntimeReflection.class, params
		);

		// act
		final Set<Class> classes = ClassBuilder.of(null, ann);

		// assert
		assertEquals(1, classes.size());
		assertEquals(Pojo.class, classes.iterator().next());
	}

	@Test
	void mustMapScanPackageClasses() {
		// arrange

		final Map<String, Object> params = new LinkedHashMap<>();
		params.put("scanClassName", "");
		params.put("scanClass", Void.class);
		params.put("scanPackage", "nativeimage.vo");

		final RuntimeReflection ann = (RuntimeReflection) AnnotationParser.annotationForMap(
			RuntimeReflection.class, params
		);

		// act
		final Set<Class> classes = ClassBuilder.of(null, ann);

		// assert
		assertEquals(1, classes.size());
		assertEquals(Pojo.class, classes.iterator().next());
	}

	@Test
	void mustMapElementClass() {
		// arrange

		final Class<Map> expectedClass = Map.class;

		final Map<String, Object> params = new LinkedHashMap<>();
		params.put("scanClassName", "");
		params.put("scanClass", Void.class);
		params.put("scanPackage", "");

		final RuntimeReflection ann = (RuntimeReflection) AnnotationParser.annotationForMap(
			RuntimeReflection.class, params
		);

		final Element element = Mockito.mock(Element.class);
		doReturn(expectedClass.getName()).when(element).toString();


		// act
		final Set<Class> classes = ClassBuilder.of(element, ann);

		// assert
		assertEquals(1, classes.size());
		assertEquals(expectedClass, classes.iterator().next());
	}

}
