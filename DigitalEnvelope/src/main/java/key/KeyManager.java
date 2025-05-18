package key;

import java.io.*;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class KeyManager {
	public static KeyPair generateRSAKeyPair() throws Exception {
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(2048);
		return keyGen.generateKeyPair();
	}

	public static SecretKey generateAESKey() throws Exception {
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(128);
		return keyGen.generateKey();
	}

	public static void saveKey(Key key, String path) throws IOException {
		try (FileOutputStream fos = new FileOutputStream(path)) {
			fos.write(key.getEncoded());
		}
	}

	public static SecretKey loadAESKey(String path) throws IOException {
		byte[] bytes = java.nio.file.Files.readAllBytes(new File(path).toPath());
		return new SecretKeySpec(bytes, "AES");
	}

	public static PublicKey loadPublicKey(String path) throws Exception {
		byte[] bytes = java.nio.file.Files.readAllBytes(new File(path).toPath());
		X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
		return KeyFactory.getInstance("RSA").generatePublic(spec);
	}

	public static PrivateKey loadPrivateKey(String path) throws Exception {
		byte[] bytes = java.nio.file.Files.readAllBytes(new File(path).toPath());
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
		return KeyFactory.getInstance("RSA").generatePrivate(spec);
	}
}