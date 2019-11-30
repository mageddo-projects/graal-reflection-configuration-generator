package nativeimage.core;

import nativeimage.Reflection;
import nativeimage.Reflections;
import nativeimage.core.domain.ReflectionConfig;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.LinkedHashSet;
import java.util.Set;

@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class AnnotationProcessor extends AbstractProcessor {

	private static final Diagnostic.Kind LEVEL = Diagnostic.Kind.NOTE;

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
			processElementsForRepeatableAnnotation(roundEnv, element);
		}
	}

	private void processElementsForRepeatableAnnotation(RoundEnvironment roundEnv, Element element) {
		final Reflections annotation = element.getAnnotation(Reflections.class);
		for (final Reflection reflection : annotation.value()) {
			if(reflection.scanPackage().isEmpty()){
				this.addElement(element, reflection);
			} else {
				for (final Element nestedElement : roundEnv.getRootElements()) {
					this.addElement(nestedElement, reflection);
					for (final Element innerClass : ElementFinder.find(nestedElement, ElementKind.CLASS)) {
						this.addElement(innerClass, reflection);
						log(Diagnostic.Kind.OTHER, String.format("innerClass=%s", innerClass));
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
			log(Diagnostic.Kind.ERROR, e.getMessage());
		} finally {
			IoUtils.safeClose(appender);
			log(Diagnostic.Kind.NOTE, "reflection-configuration, written-objects= " + this.classes.size());
			log(Diagnostic.Kind.OTHER, "objects=%s", this.classes);
		}
	}

	private String getClassPackage() {
		return this.classPackage == null ? "graal-reflection-configuration" : this.classPackage;
	}

	private void log(Diagnostic.Kind level, String format, Object ... args) {
		if(level.ordinal() > LEVEL.ordinal()){
			return;
		}
		messager.printMessage(level, String.format(format, args));
	}

}
