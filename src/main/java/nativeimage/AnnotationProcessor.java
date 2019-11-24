package nativeimage;

import nativeimage.core.ReflectionConfig;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;

@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class AnnotationProcessor extends AbstractProcessor {

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		try {
			final ReflectionConfigAppender appender = new ReflectionConfigAppenderAnnotationProcessing(
				processingEnv.getFiler().createSourceFile("cyz")
			);

			final Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(RuntimeReflection.class);
			for (Element element : elements) {
				System.out.printf(":> %s%n", element);
				for (ReflectionConfig reflectionConfig : ReflectionConfigBuilder.of(element)) {
					appender.append(reflectionConfig);
				}
			}
			return true;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
