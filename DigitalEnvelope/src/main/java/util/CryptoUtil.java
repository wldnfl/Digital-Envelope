package util;

import java.security.*;
import javax.crypto.*;
import java.util.Base64;

public class CryptoUtil {

	// SHA-256 해시 생성
	public static byte[] hashSHA256(byte[] data) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		return digest.digest(data);
	}

	// RSA 키페어 생성 (2048bit)
	public static KeyPair generateRSAKeyPair() throws Exception {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(2048);
		return keyGen.generateKeyPair();
	}

	// AES 256비트 키 생성
	public static SecretKey generateAESKey() throws Exception {
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(256);
		return keyGen.generateKey();
	}

	// RSA로 암호화
	public static byte[] encryptRSA(byte[] data, PublicKey pubKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		return cipher.doFinal(data);
	}

	// RSA로 복호화
	public static byte[] decryptRSA(byte[] encrypted, PrivateKey privKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privKey);
		return cipher.doFinal(encrypted);
	}

	// AES 암호화
	public static byte[] encryptAES(byte[] data, SecretKey secretKey) throws Exception {
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		return cipher.doFinal(data);
	}

	// AES 복호화
	public static byte[] decryptAES(byte[] encrypted, SecretKey secretKey) throws Exception {
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		return cipher.doFinal(encrypted);
	}

	// 전자서명 생성 (RSA 개인키로 해시 암호화)
	public static byte[] sign(byte[] hash, PrivateKey privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		return cipher.doFinal(hash);
	}

	// 전자서명 검증 (RSA 공개키로 서명 복호화)
	public static byte[] verify(byte[] signedHash, PublicKey publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		return cipher.doFinal(signedHash);
	}

	// Base64 인코딩
	public static String toBase64(byte[] data) {
		return Base64.getEncoder().encodeToString(data);
	}

	// Base64 디코딩
	public static byte[] fromBase64(String base64) {
		return Base64.getDecoder().decode(base64);
	}
}
