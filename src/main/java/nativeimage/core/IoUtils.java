package nativeimage.core;

import java.io.Closeable;
import java.io.IOException;

public class IoUtils {
	public static void safeClose(Closeable c){
		if(c == null){
			return;
		}
		try {
			c.close();
		} catch (IOException e) {}
	}
}
