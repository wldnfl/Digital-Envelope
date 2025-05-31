package util;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class SecretKeyManager {

	private static final String KEY_ALGORITHM = "AES";
	private static final int KEY_SIZE = 128;
	private static final String SECRET_KEY_FILE = "WEB-INF/keys/secret.key";

	// SecretKey 생성
	public static SecretKey generateKey() throws NoSuchAlgorithmException {
		KeyGenerator keyGen = KeyGenerator.getInstance(KEY_ALGORITHM);
		keyGen.init(KEY_SIZE);
		return keyGen.generateKey();
	}

	// SecretKey 파일로 저장 (Base64 인코딩 후 텍스트로 저장)
	public static void saveKey(SecretKey secretKey, String realPath) throws IOException {
		String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
		File file = new File(realPath, SECRET_KEY_FILE);
		file.getParentFile().mkdirs(); // 디렉토리 없으면 생성
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			writer.write(encodedKey);
		}
	}

	// SecretKey 파일에서 불러오기
	public static SecretKey loadKey(String realPath) throws IOException {
		File file = new File(realPath, SECRET_KEY_FILE);
		if (!file.exists()) {
			throw new FileNotFoundException("대칭키 파일이 존재하지 않습니다: " + file.getAbsolutePath());
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String encodedKey = reader.readLine();
			byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
			return new javax.crypto.spec.SecretKeySpec(decodedKey, KEY_ALGORITHM);
		}
	}

	// 생성 or 로드 (존재하면 로드, 없으면 생성 후 저장)
	public static SecretKey getOrCreateKey(String realPath) throws IOException, NoSuchAlgorithmException {
		File file = new File(realPath, SECRET_KEY_FILE);
		if (file.exists()) {
			return loadKey(realPath);
		} else {
			SecretKey key = generateKey();
			saveKey(key, realPath);
			return key;
		}
	}
}
