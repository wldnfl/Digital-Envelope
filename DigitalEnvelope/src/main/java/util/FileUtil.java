package util;

import java.io.*;

public class FileUtil {
	public static void writeToFile(String path, byte[] data) throws IOException {
		try (FileOutputStream fos = new FileOutputStream(path)) {
			fos.write(data);
		}
	}

	public static byte[] readFromFile(String path) throws IOException {
		return java.nio.file.Files.readAllBytes(new File(path).toPath());
	}
}