package util;

import java.security.*;
import javax.crypto.*;
import java.io.*;

public class KeyManager {
	private static final String RSA_KEY_FILE = "rsa_keypair.dat";
	private static final String AES_KEY_FILE = "aes_key.dat";

	// RSA 키 생성
	public static void generateRSAKeyPair(String keyFolderPath) throws Exception {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(2048);
		KeyPair keyPair = keyGen.generateKeyPair();

		File rsaFile = new File(keyFolderPath, RSA_KEY_FILE);
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rsaFile))) {
			oos.writeObject(keyPair);
		}
	}

	// RSA 키 복구
	public static KeyPair loadRSAKeyPair(String keyFolderPath) throws Exception {
		File rsaFile = new File(keyFolderPath, RSA_KEY_FILE);
		if (!rsaFile.exists() || !rsaFile.canRead()) {
			throw new FileNotFoundException("RSA 키 파일이 존재하지 않거나 읽을 수 없습니다!");
		}

		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(rsaFile))) {
			return (KeyPair) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			throw new IOException("RSA 키 파일 읽기 중 오류 발생: " + e.getMessage(), e);
		}
	}

	// AES 키 생성
	public static SecretKey generateAESKey(String keyFolderPath) throws Exception {
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(128);
		SecretKey secretKey = keyGen.generateKey();

		File aesFile = new File(keyFolderPath, AES_KEY_FILE);
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(aesFile))) {
			oos.writeObject(secretKey);
		}

		return secretKey;
	}

	// AES 키 복구
	public static SecretKey loadAESKey(String keyFolderPath) throws Exception {
		File aesFile = new File(keyFolderPath, AES_KEY_FILE);
		if (!aesFile.exists() || !aesFile.canRead()) {
			throw new FileNotFoundException("AES 키 파일이 존재하지 않거나 읽을 수 없습니다!");
		}

		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(aesFile))) {
			return (SecretKey) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			throw new IOException("AES 키 파일 읽기 중 오류 발생: " + e.getMessage(), e);
		}
	}

}
