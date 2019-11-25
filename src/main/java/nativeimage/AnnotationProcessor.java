package nativeimage;

import nativeimage.core.ReflectionConfig;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class AnnotationProcessor extends AbstractProcessor {

	private Set<ReflectionConfig> classes;
	private Messager messager;

	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
		this.messager = this.processingEnv.getMessager();
		this.classes = new LinkedHashSet<>();
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		final boolean processingOver = roundEnv.processingOver();
		messager.printMessage(Diagnostic.Kind.NOTE, String.format(">> process: %s, annotations=%s %n", roundEnv, annotations));
		processElementsForAnnotation(annotations.isEmpty(), roundEnv.getElementsAnnotatedWith(RuntimeReflection.class));
		processElementsForRepeatableAnnotation(roundEnv);
		if (processingOver) {
			writeObjects();
		}
		return false;
	}

	private void processElementsForRepeatableAnnotation(RoundEnvironment roundEnv) {
		final Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(RepeatbleRuntimeReflection.class);
		for (Element element : elements) {
			final RepeatbleRuntimeReflection annotation = element.getAnnotation(RepeatbleRuntimeReflection.class);
			for (RuntimeReflection runtimeReflection : annotation.value()) {
				addElement(element, runtimeReflection);
			}
		}
	}

	private void processElementsForAnnotation(
		final boolean hasAnnotations, final Set<? extends Element> annotatedElements
	) {
		if (!hasAnnotations) {
			for (Element element : annotatedElements) {
				addElement(element, element.getAnnotation(RuntimeReflection.class));
			}
		}
	}

	private void addElement(Element element, RuntimeReflection annotation) {
		messager.printMessage(Diagnostic.Kind.NOTE, String.format(":> %s%n", element));
		classes.addAll(ReflectionConfigBuilder.of(element, annotation));
	}

	private void writeObjects() {
		messager.printMessage(Diagnostic.Kind.NOTE, "writing objects: " + this.classes.size());
		ReflectionConfigAppenderAnnotationProcessing appender = null;
		try {
			appender = new ReflectionConfigAppenderAnnotationProcessing(processingEnv);
			for (ReflectionConfig config : this.classes) {
				appender.append(config);
			}
			messager.printMessage(Diagnostic.Kind.NOTE, "completed");
		} catch (Throwable e){
			messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
		} finally {
			if(appender != null){
				try {
					appender.close();
					messager.printMessage(Diagnostic.Kind.NOTE, "closed");
				} catch (IOException e) {}
			}
		}
	}
}
