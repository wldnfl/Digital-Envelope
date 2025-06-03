package util;

import java.io.*;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import jakarta.servlet.ServletContext;

public class KeyManager {
	// RSA 알고리즘 및 키 크기
	private static final String RSA_ALGORITHM = "RSA";
	private static final int RSA_KEY_SIZE = 1024;

	// 키 저장 디렉터리 및 파일명
	private static final String KEY_DIR = "/WEB-INF/keys";
	private static final String PUBLIC_KEY_FILE = "public.key";
	private static final String PRIVATE_KEY_FILE = "private.key";

	private final ServletContext context;

	public KeyManager(ServletContext context) {
		this.context = context;
	}

	// 기존 키가 있으면 불러오고, 없으면 새로 생성
	public KeyPair getOrCreateKeyPair() throws Exception {
		String realDir = context.getRealPath(KEY_DIR);
		File dir = new File(realDir);
		if (!dir.exists())
			dir.mkdirs();

		File pubFile = new File(dir, PUBLIC_KEY_FILE);
		File priFile = new File(dir, PRIVATE_KEY_FILE);

		if (pubFile.exists() && priFile.exists()) {
			return loadKeyPair(pubFile, priFile); // 키 로드
		} else {
			KeyPair keyPair = generateKeyPair(); // 키 생성
			saveKeyPair(keyPair, pubFile, priFile); // 키 저장
			return keyPair;
		}
	}

	// RSA 키 쌍 생성
	private KeyPair generateKeyPair() throws Exception {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance(RSA_ALGORITHM);
		keyGen.initialize(RSA_KEY_SIZE);
		return keyGen.generateKeyPair();
	}

	// 키 쌍을 파일로 저장
	private void saveKeyPair(KeyPair keyPair, File pubFile, File priFile) throws IOException {
		Files.write(pubFile.toPath(), keyPair.getPublic().getEncoded());
		Files.write(priFile.toPath(), keyPair.getPrivate().getEncoded());
	}

	// 키 파일로부터 키 쌍을 불러오기
	private KeyPair loadKeyPair(File pubFile, File priFile) throws Exception {
		byte[] pubBytes = Files.readAllBytes(pubFile.toPath());
		byte[] priBytes = Files.readAllBytes(priFile.toPath());

		KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
		PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(pubBytes));
		PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(priBytes));

		return new KeyPair(publicKey, privateKey);
	}

	// 키 파일 존재 여부 확인
	public boolean isKeyPairExist() {
		String realDir = context.getRealPath(KEY_DIR);
		File dir = new File(realDir);
		File pubFile = new File(dir, PUBLIC_KEY_FILE);
		System.out.println("[KeyManager] 키 경로: " + pubFile.getAbsolutePath());

		if (!pubFile.exists())
			return false;

		File priFile = new File(dir, PRIVATE_KEY_FILE);
		System.out.println("[KeyManager] 공개키 존재 여부: " + pubFile.exists());
		System.out.println("[KeyManager] 개인키 존재 여부: " + priFile.exists());

		return priFile.exists();
	}

	// 키 상태 반환 (존재 여부 메시지)
	public String getKeyStatus() {
		if (isKeyPairExist()) {
			return "키가 존재합니다.";
		} else {
			return "키가 없습니다.";
		}
	}

	// 키 파일 삭제
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

	// 새 키 쌍 생성 및 저장
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

	// 키 쌍을 강제로 불러오기 (존재 안 하면 예외 발생)
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
