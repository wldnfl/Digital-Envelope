package model;

import java.util.Base64;

// 전자봉투 데이터 모델 (암호문과 암호화된 대칭키 보관)
public class DigitalEnvelope {
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
		return Base64.getEncoder().encodeToString(encryptedDocument);
	}

	public String getEncryptedSecretKeyBase64() {
		return Base64.getEncoder().encodeToString(encryptedSecretKey);
	}

	// Base64 -> DigitalEnvelope 객체 복원
	public static DigitalEnvelope fromBase64(String encryptedDocumentBase64, String encryptedSecretKeyBase64) {
		byte[] encryptedDocument = Base64.getDecoder().decode(encryptedDocumentBase64);
		byte[] encryptedSecretKey = Base64.getDecoder().decode(encryptedSecretKeyBase64);
		return new DigitalEnvelope(encryptedDocument, encryptedSecretKey);
	}
}
