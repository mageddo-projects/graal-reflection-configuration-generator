package nativeimage.core;

import nativeimage.RepeatableRuntimeReflection;
import nativeimage.RuntimeReflection;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
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
		processElementsForAnnotation(annotations.isEmpty(), roundEnv.getElementsAnnotatedWith(RuntimeReflection.class));
		processElementsForRepeatableAnnotation(roundEnv);
		if (processingOver) {
			writeObjects();
		}
		return false;
	}

	private void processElementsForRepeatableAnnotation(RoundEnvironment roundEnv) {
		final Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(RepeatableRuntimeReflection.class);
		for (Element element : elements) {
			final RepeatableRuntimeReflection annotation = element.getAnnotation(RepeatableRuntimeReflection.class);
			for (RuntimeReflection runtimeReflection : annotation.value()) {
				if(runtimeReflection.scanPackage().isEmpty()){
					addElement(element, runtimeReflection);
				} else {
					for (Element nestedElements : roundEnv.getRootElements()) {
						addElement(nestedElements, runtimeReflection);
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
				addElement(element, element.getAnnotation(RuntimeReflection.class));
			}
		}
	}

	private void addElement(Element element, RuntimeReflection annotation) {
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
