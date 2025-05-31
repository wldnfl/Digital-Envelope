package util;

import java.io.*;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import jakarta.servlet.ServletContext;

public class KeyManager {
	private static final String RSA_ALGORITHM = "RSA";
	private static final int RSA_KEY_SIZE = 1024;

	private static final String KEY_DIR = "/WEB-INF/keys";
	private static final String PUBLIC_KEY_FILE = "public.key";
	private static final String PRIVATE_KEY_FILE = "private.key";

	private final ServletContext context;

	public KeyManager(ServletContext context) {
		this.context = context;
	}

	public KeyPair getOrCreateKeyPair() throws Exception {
		String realDir = context.getRealPath(KEY_DIR);
		File dir = new File(realDir);
		if (!dir.exists())
			dir.mkdirs();

		File pubFile = new File(dir, PUBLIC_KEY_FILE);
		File priFile = new File(dir, PRIVATE_KEY_FILE);

		if (pubFile.exists() && priFile.exists()) {
			return loadKeyPair(pubFile, priFile);
		} else {
			KeyPair keyPair = generateKeyPair();
			saveKeyPair(keyPair, pubFile, priFile);
			return keyPair;
		}
	}

	private KeyPair generateKeyPair() throws Exception {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance(RSA_ALGORITHM);
		keyGen.initialize(RSA_KEY_SIZE);
		return keyGen.generateKeyPair();
	}

	private void saveKeyPair(KeyPair keyPair, File pubFile, File priFile) throws IOException {
		Files.write(pubFile.toPath(), keyPair.getPublic().getEncoded());
		Files.write(priFile.toPath(), keyPair.getPrivate().getEncoded());
	}

	private KeyPair loadKeyPair(File pubFile, File priFile) throws Exception {
		byte[] pubBytes = Files.readAllBytes(pubFile.toPath());
		byte[] priBytes = Files.readAllBytes(priFile.toPath());

		KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
		PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(pubBytes));
		PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(priBytes));

		return new KeyPair(publicKey, privateKey);
	}

	// 키 존재 여부 체크
	public boolean isKeyPairExist() {
		String realDir = context.getRealPath(KEY_DIR);
		File dir = new File(realDir);
		File pubFile = new File(dir, PUBLIC_KEY_FILE);
		File priFile = new File(dir, PRIVATE_KEY_FILE);

		System.out.println("[KeyManager] 키 경로: " + pubFile.getAbsolutePath());
		System.out.println("[KeyManager] 공개키 존재 여부: " + pubFile.exists());
		System.out.println("[KeyManager] 개인키 존재 여부: " + priFile.exists());
		return pubFile.exists() && priFile.exists();
	}

	public String getKeyStatus() {
		if (isKeyPairExist()) {
			return "키가 존재합니다.";
		} else {
			return "키가 없습니다.";
		}
	}

	public void deleteKeyPair() throws IOException {
		String realDir = context.getRealPath(KEY_DIR);
		File dir = new File(realDir);
		File pubFile = new File(dir, PUBLIC_KEY_FILE);
		File priFile = new File(dir, PRIVATE_KEY_FILE);
		if (pubFile.exists())
			pubFile.delete();
		if (priFile.exists())
			priFile.delete();
	}

	public void createKeyPair() throws Exception {
		String realDir = context.getRealPath(KEY_DIR);
		File dir = new File(realDir);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		File pubFile = new File(dir, PUBLIC_KEY_FILE);
		File priFile = new File(dir, PRIVATE_KEY_FILE);

		KeyPair keyPair = generateKeyPair();
		saveKeyPair(keyPair, pubFile, priFile);
	}

	public KeyPair loadKeyPair() throws Exception {
		String realDir = context.getRealPath(KEY_DIR);
		File dir = new File(realDir);
		File pubFile = new File(dir, PUBLIC_KEY_FILE);
		File priFile = new File(dir, PRIVATE_KEY_FILE);

		if (!pubFile.exists() || !priFile.exists()) {
			throw new IllegalStateException("키 파일이 존재하지 않습니다.");
		}

		return loadKeyPair(pubFile, priFile);
	}
}
