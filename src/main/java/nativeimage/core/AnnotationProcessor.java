package nativeimage.core;

import nativeimage.Reflection;
import nativeimage.Reflections;
import nativeimage.core.domain.ReflectionConfig;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;
import java.util.LinkedHashSet;
import java.util.Set;

@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class AnnotationProcessor extends AbstractProcessor {

	private Set<ReflectionConfig> classes;
	private Messager messager;
	private String classPackage;

	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
		this.messager = this.processingEnv.getMessager();
		this.classes = new LinkedHashSet<>();
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		final boolean processingOver = roundEnv.processingOver();
		processElementsForRepeatableAnnotation(roundEnv);
		processElementsForAnnotation(annotations.isEmpty(), roundEnv.getElementsAnnotatedWith(Reflection.class));
		if (processingOver) {
			writeObjects();
		}
		return false;
	}

	private void processElementsForRepeatableAnnotation(RoundEnvironment roundEnv) {
		final Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Reflections.class);
		for (Element element : elements) {

			final Reflections annotation = element.getAnnotation(Reflections.class);
			for (Reflection reflection : annotation.value()) {
				if(reflection.scanPackage().isEmpty()){
					addElement(element, reflection);
				} else {
					for (Element nestedElements : roundEnv.getRootElements()) {
						addElement(nestedElements, reflection);
					}
				}
			}
		}
	}

	private void processElementsForAnnotation(
		final boolean hasAnnotations, final Set<? extends Element> annotatedElements
	) {
		if (!hasAnnotations) {
			for (Element element : annotatedElements) {
				addElement(element, element.getAnnotation(Reflection.class));
			}
		}
	}

	private void addElement(Element element, Reflection annotation) {
		this.classPackage = this.classPackage == null ? ClassUtils.getClassPackage(element.toString()) : this.classPackage;
		this.classes.addAll(ReflectionConfigBuilder.of(element, annotation));
	}

	private void writeObjects() {
		ReflectionConfigAppenderAnnotationProcessing appender = null;
		try {
			appender = new ReflectionConfigAppenderAnnotationProcessing(this.processingEnv, getClassPackage());
			for (ReflectionConfig config : this.classes) {
				appender.append(config);
			}
		} catch (Throwable e){
			messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
		} finally {
			IoUtils.safeClose(appender);
			messager.printMessage(Diagnostic.Kind.NOTE, "reflection-configuration, written-objects= " + this.classes.size());
		}
	}

	private String getClassPackage() {
		return this.classPackage == null ? "graal-reflection-configuration" : this.classPackage;
	}

}
