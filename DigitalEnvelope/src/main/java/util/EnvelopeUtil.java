package util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.PrivateKey;
import java.security.PublicKey;

public class EnvelopeUtil {

	// 문서 암호화 + 대칭키 암호화 -> 전자봉투 생성
	public static DigitalEnvelope createEnvelope(byte[] documentData, PublicKey receiverPublicKey, SecretKey secretKey)
			throws Exception {
		// 1. 문서 대칭키(AES)로 암호화
		byte[] encryptedDocument = encryptDocument(documentData, secretKey);

		// 2. 대칭키를 수신자 공개키(RSA)로 암호화
		byte[] encryptedSecretKey = encryptSecretKey(secretKey.getEncoded(), receiverPublicKey);

		return new DigitalEnvelope(encryptedDocument, encryptedSecretKey);
	}

	// 전자봉투 복호화 및 문서 복원
	public static byte[] decryptEnvelope(DigitalEnvelope envelope, PrivateKey receiverPrivateKey) throws Exception {
		// 1. RSA 개인키로 대칭키 복호화
		byte[] decryptedSecretKeyBytes = decryptSecretKey(envelope.getEncryptedSecretKey(), receiverPrivateKey);
		SecretKey originalSecretKey = new SecretKeySpec(decryptedSecretKeyBytes, "AES");

		// 2. AES 대칭키로 문서 복호화
		return decryptDocument(envelope.getEncryptedDocument(), originalSecretKey);
	}

	private static byte[] encryptDocument(byte[] data, SecretKey secretKey) throws Exception {
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		return cipher.doFinal(data);
	}

	private static byte[] decryptDocument(byte[] encryptedData, SecretKey secretKey) throws Exception {
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		return cipher.doFinal(encryptedData);
	}

	private static byte[] encryptSecretKey(byte[] secretKeyBytes, PublicKey publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(secretKeyBytes);
	}

	private static byte[] decryptSecretKey(byte[] encryptedSecretKeyBytes, PrivateKey privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(encryptedSecretKeyBytes);
	}

	// 전자봉투 데이터 모델 (암호문과 암호화된 대칭키 보관)
	public static class DigitalEnvelope {
		private final byte[] encryptedDocument;
		private final byte[] encryptedSecretKey;

		public DigitalEnvelope(byte[] encryptedDocument, byte[] encryptedSecretKey) {
			this.encryptedDocument = encryptedDocument;
			this.encryptedSecretKey = encryptedSecretKey;
		}

		public byte[] getEncryptedDocument() {
			return encryptedDocument;
		}

		public byte[] getEncryptedSecretKey() {
			return encryptedSecretKey;
		}

		// Base64 인코딩 문자열로 저장/전송용 변환
		public String getEncryptedDocumentBase64() {
			return java.util.Base64.getEncoder().encodeToString(encryptedDocument);
		}

		public String getEncryptedSecretKeyBase64() {
			return java.util.Base64.getEncoder().encodeToString(encryptedSecretKey);
		}

		// Base64 -> DigitalEnvelope 객체 복원
		public static DigitalEnvelope fromBase64(String encryptedDocumentBase64, String encryptedSecretKeyBase64) {
			byte[] encryptedDocument = java.util.Base64.getDecoder().decode(encryptedDocumentBase64);
			byte[] encryptedSecretKey = java.util.Base64.getDecoder().decode(encryptedSecretKeyBase64);
			return new DigitalEnvelope(encryptedDocument, encryptedSecretKey);
		}
	}
}
