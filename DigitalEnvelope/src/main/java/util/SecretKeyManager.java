package util;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import jakarta.servlet.ServletContext;
import exception.KeyDeleteException;
import exception.KeyNotExistException;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class SecretKeyManager {

	private static final String KEY_ALGORITHM = "AES";
	private static final int KEY_SIZE = 128;
	private static final String KEY_DIR = "/WEB-INF/keys";
	private static final String SECRET_KEY_FILE = "secret.key";

	private final File keyFile;

	public SecretKeyManager(ServletContext context) {
		String realDir = context.getRealPath(KEY_DIR);
		File dir = new File(realDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		keyFile = new File(dir, SECRET_KEY_FILE);
	}

	// SecretKey 생성
	public SecretKey generateKey() throws NoSuchAlgorithmException {
		KeyGenerator keyGen = KeyGenerator.getInstance(KEY_ALGORITHM);
		keyGen.init(KEY_SIZE);
		return keyGen.generateKey();
	}

	// SecretKey 파일로 저장 (Base64 인코딩 후 텍스트로 저장)
	public void saveKey(SecretKey secretKey) throws IOException {
		String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(keyFile))) {
			writer.write(encodedKey);
		}
	}

	// SecretKey 파일에서 불러오기
	public SecretKey loadKey() throws IOException {
		if (!keyFile.exists()) {
			throw new KeyNotExistException();
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(keyFile))) {
			String encodedKey = reader.readLine();
			byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
			return new SecretKeySpec(decodedKey, KEY_ALGORITHM);
		}
	}

	// 생성 or 로드 (존재하면 로드, 없으면 생성 후 저장)
	public SecretKey getOrCreateKey() throws IOException, NoSuchAlgorithmException {
		if (keyFile.exists()) {
			return loadKey();
		} else {
			SecretKey key = generateKey();
			saveKey(key);
			return key;
		}
	}

	public boolean isKeyExist() {
		return keyFile.exists();
	}

	public void deleteKey() throws IOException {
		if (keyFile.exists() && !keyFile.delete()) {
			throw new KeyDeleteException();
		}
	}
}
