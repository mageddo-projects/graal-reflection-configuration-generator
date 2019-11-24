package nativeimage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import nativeimage.core.ReflectionConfig;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.tools.FileObject;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

public class ReflectionConfigAppenderAnnotationProcessing implements ReflectionConfigAppender, Closeable {

	private final FileObject fileObject;
	private final ObjectMapper objectMapper;
	private final OutputStream out;

	public ReflectionConfigAppenderAnnotationProcessing(ProcessingEnvironment processingEnv) throws IOException {
		this.fileObject = processingEnv
			.getFiler()
			.createResource(StandardLocation.SOURCE_OUTPUT, "", "reflect.json")
		;
		this.objectMapper = new ObjectMapper()
			.disable(SerializationFeature.CLOSE_CLOSEABLE)
			.enable(SerializationFeature.INDENT_OUTPUT)
		;
		this.out = fileObject.openOutputStream();
	}

	@Override
	public void append(ReflectionConfig reflectionConfig) {
		try {
			this.objectMapper.writeValue(this.out, reflectionConfig);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void close() throws IOException {
		this.out.close();
	}
}
