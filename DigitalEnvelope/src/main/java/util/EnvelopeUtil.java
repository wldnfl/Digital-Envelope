package util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import exception.EnvelopeCreationException;
import exception.EnvelopeDecryptionException;
import model.DigitalEnvelope;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Objects;

public class EnvelopeUtil {

	private static final String AES_ALGORITHM = "AES";
	private static final String RSA_ALGORITHM = "RSA";

	// 문서 암호화 + 대칭키 암호화 -> 전자봉투 생성
	public static DigitalEnvelope createEnvelope(byte[] documentData, PublicKey receiverPublicKey,
			SecretKey secretKey) {
		Objects.requireNonNull(documentData, "documentData must not be null");
		Objects.requireNonNull(receiverPublicKey, "receiverPublicKey must not be null");
		Objects.requireNonNull(secretKey, "secretKey must not be null");
		
		try {
			byte[] encryptedDocument = encryptDocument(documentData, secretKey);
			byte[] encryptedSecretKey = encryptSecretKey(secretKey.getEncoded(), receiverPublicKey);
			return new DigitalEnvelope(encryptedDocument, encryptedSecretKey);
		} catch (Exception e) {
			throw new EnvelopeCreationException(e);
		}
	}

	// 전자봉투 복호화 및 문서 복원
	public static byte[] decryptEnvelope(DigitalEnvelope envelope, PrivateKey receiverPrivateKey) {
		Objects.requireNonNull(envelope, "envelope must not be null");
		Objects.requireNonNull(receiverPrivateKey, "receiverPrivateKey must not be null");

		try {
			byte[] decryptedSecretKeyBytes = decryptSecretKey(envelope.getEncryptedSecretKey(), receiverPrivateKey);
			SecretKey originalSecretKey = new SecretKeySpec(decryptedSecretKeyBytes, AES_ALGORITHM);
			return decryptDocument(envelope.getEncryptedDocument(), originalSecretKey);
		} catch (Exception e) {
			throw new EnvelopeDecryptionException();
		}
	}

	private static byte[] encryptDocument(byte[] data, SecretKey secretKey) throws Exception {
		Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		return cipher.doFinal(data);
	}

	private static byte[] decryptDocument(byte[] encryptedData, SecretKey secretKey) throws Exception {
		Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		return cipher.doFinal(encryptedData);
	}

	private static byte[] encryptSecretKey(byte[] secretKeyBytes, PublicKey publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(secretKeyBytes);
	}

	private static byte[] decryptSecretKey(byte[] encryptedSecretKeyBytes, PrivateKey privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(encryptedSecretKeyBytes);
	}

}
