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
		processElementsForAnnotation(roundEnv);
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
		final Reflections reflections = element.getAnnotation(Reflections.class);
		for (final Reflection reflection : reflections.value()) {
			processElementsForAnnotation(roundEnv, element, reflection);
		}
	}

	private void processElementsForAnnotation(RoundEnvironment roundEnv) {
		for (Element element : roundEnv.getElementsAnnotatedWith(Reflection.class)) {
			processElementsForAnnotation(roundEnv, element, element.getAnnotation(Reflection.class));
		}
	}

	private void processElementsForAnnotation(RoundEnvironment roundEnv, Element element, Reflection reflection) {
		if(reflection.scanPackage().isEmpty()){
			this.addElement(element, reflection);
		} else {
			for (final Element nestedElement : roundEnv.getRootElements()) {
				this.addElement(nestedElement, reflection);
				for (final Element innerClass : ElementFinder.find(nestedElement, ElementKind.CLASS)) {
					this.addElement(innerClass, reflection);
					log(Diagnostic.Kind.OTHER, "innerClass=%s", innerClass);
				}
			}
		}
	}

	private void addElement(Element element, Reflection annotation) {
//		final Symbol.ClassSymbol symbol = (Symbol.ClassSymbol) element;
//		((Symbol.ClassSymbol) element).sourcefile.de
		log(
			Diagnostic.Kind.OTHER,
			"m=addElement, asType=%s, kind=%s, simpleName=%s, enclosing=%s, clazz=%s",
			element.asType(), element.getKind(), element.getSimpleName(),
			element.getEnclosingElement(), element.getClass()
		);
		this.classPackage = this.classPackage == null ? ClassUtils.getClassPackage(element.toString()) : this.classPackage;
		for (ReflectionConfig config : ReflectionConfigBuilder.of(element, annotation)) {
			this.classes.remove(config);
			this.classes.add(config);
		}
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
