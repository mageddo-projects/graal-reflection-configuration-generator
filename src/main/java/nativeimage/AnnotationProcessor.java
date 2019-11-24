package nativeimage;

import nativeimage.core.ReflectionConfig;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class AnnotationProcessor extends AbstractProcessor {

	private Set<ReflectionConfig> classes = new LinkedHashSet<>();
	private Messager messager;

	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
		this.messager = this.processingEnv.getMessager();
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		messager.printMessage(Diagnostic.Kind.NOTE, String.format(">> process: %s, annotations=%s %n", roundEnv, annotations));
		if (annotations.isEmpty()) {
			return true;
		}
		final Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(RuntimeReflection.class);
		for (Element element : elements) {
			messager.printMessage(Diagnostic.Kind.NOTE, String.format(":> %s%n", element));
			classes.addAll(ReflectionConfigBuilder.of(element));
		}

		if (roundEnv.processingOver()) {
			writeObjects();
		}
		return false;
	}

	private void writeObjects() {
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
