package nativeimage.core;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SequenceWriter;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.Closeable;
import java.io.IOException;

public class ReflectionConfigAppenderAnnotationProcessing implements ReflectionConfigAppender, Closeable {

	private final FileObject fileObject;
	private final SequenceWriter writer;

	public ReflectionConfigAppenderAnnotationProcessing(ProcessingEnvironment processingEnv, String classPackage) throws IOException {
		this.fileObject = processingEnv
			.getFiler()
			.createResource(
				StandardLocation.CLASS_OUTPUT, "", String.format("META-INF/%s/reflect.json", classPackage)
			)
		;

		final DefaultPrettyPrinter.Indenter indenter = new DefaultIndenter(
			"  ", DefaultIndenter.SYS_LF
		);
		final DefaultPrettyPrinter printer = new DefaultPrettyPrinter();
		printer.indentObjectsWith(indenter);
		printer.indentArraysWith(indenter);

		this.writer = new ObjectMapper()
			.writer(printer)
			.writeValuesAsArray(fileObject.openOutputStream())
		;
	}

	@Override
	public void append(ReflectionConfig reflectionConfig) {
		try {
			this.writer.write(reflectionConfig);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void close() throws IOException {
		this.writer.close();
	}
}
