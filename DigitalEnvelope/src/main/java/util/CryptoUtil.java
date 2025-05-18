package util;

import javax.crypto.*;
import java.security.*;

public class CryptoUtil {
	public static byte[] encryptAES(byte[] data, SecretKey key) throws Exception {
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return cipher.doFinal(data);
	}

	public static byte[] decryptAES(byte[] data, SecretKey key) throws Exception {
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, key);
		return cipher.doFinal(data);
	}

	public static byte[] encryptRSA(byte[] data, PublicKey key) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return cipher.doFinal(data);
	}

	public static byte[] decryptRSA(byte[] data, PrivateKey key) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, key);
		return cipher.doFinal(data);
	}

	public static byte[] signData(byte[] data, PrivateKey key) throws Exception {
		Signature signer = Signature.getInstance("SHA256withRSA");
		signer.initSign(key);
		signer.update(data);
		return signer.sign();
	}

	public static boolean verifySignature(byte[] data, byte[] signature, PublicKey key) throws Exception {
		Signature verifier = Signature.getInstance("SHA256withRSA");
		verifier.initVerify(key);
		verifier.update(data);
		return verifier.verify(signature);
	}
}
