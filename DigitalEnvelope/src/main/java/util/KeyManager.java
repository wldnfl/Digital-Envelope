package util;

import java.io.*;
import java.security.*;
import jakarta.servlet.ServletContext;
import exception.KeyNotExistException;

public class KeyManager {
	private static final String RSA_ALGORITHM = "RSA";
	private static final int RSA_KEY_SIZE = 1024;

	private static final String KEY_DIR = "/WEB-INF/keys";
	private static final String PUBLIC_KEY_FILE = "public.key";
	private static final String PRIVATE_KEY_FILE = "private.key";

	private final File pubFile;
	private final File priFile;

	public KeyManager(ServletContext context) {
		String realDir = context.getRealPath(KEY_DIR);
		File dir = new File(realDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		pubFile = new File(dir, PUBLIC_KEY_FILE);
		priFile = new File(dir, PRIVATE_KEY_FILE);
	}

	// 기존 키 있으면 불러오고 없으면 새로 생성
	public KeyPair getOrCreateKeyPair() throws Exception {
		if (pubFile.exists() && priFile.exists()) {
			return loadKeyPair();
		} else {
			KeyPair keyPair = generateKeyPair();
			saveKeyPair(keyPair);
			return keyPair;
		}
	}

	// RSA 키 생성
	private KeyPair generateKeyPair() throws NoSuchAlgorithmException {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance(RSA_ALGORITHM);
		keyGen.initialize(RSA_KEY_SIZE);
		return keyGen.generateKeyPair();
	}

	// 키 쌍 저장 (ObjectOutputStream 사용)
	public void saveKeyPair(KeyPair keyPair) throws IOException {
		try (ObjectOutputStream oosPub = new ObjectOutputStream(new FileOutputStream(pubFile));
				ObjectOutputStream oosPri = new ObjectOutputStream(new FileOutputStream(priFile))) {
			oosPub.writeObject(keyPair.getPublic());
			oosPri.writeObject(keyPair.getPrivate());
		}
	}

	// 키 쌍 로드 (ObjectInputStream 사용)
	public KeyPair loadKeyPair() throws IOException, ClassNotFoundException {
		if (!pubFile.exists() || !priFile.exists()) {
			throw new KeyNotExistException();
		}

		try (ObjectInputStream oisPub = new ObjectInputStream(new FileInputStream(pubFile));
				ObjectInputStream oisPri = new ObjectInputStream(new FileInputStream(priFile))) {
			PublicKey publicKey = (PublicKey) oisPub.readObject();
			PrivateKey privateKey = (PrivateKey) oisPri.readObject();
			return new KeyPair(publicKey, privateKey);
		}
	}

	public boolean isKeyPairExist() {
		System.out.println("[KeyManager] 공개키 존재 여부: " + pubFile.exists());
		System.out.println("[KeyManager] 개인키 존재 여부: " + priFile.exists());
		return pubFile.exists() && priFile.exists();
	}

	public String getKeyStatus() {
		return isKeyPairExist() ? "키가 존재합니다." : "키가 없습니다.";
	}

	public void deleteKeyPair() {
		if (pubFile.exists())
			pubFile.delete();
		if (priFile.exists())
			priFile.delete();
	}

	public void createKeyPair() throws Exception {
		KeyPair keyPair = generateKeyPair();
		saveKeyPair(keyPair);
	}
}
