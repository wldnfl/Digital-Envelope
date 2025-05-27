package util;

import model.Envelope;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.security.*;

public class EnvelopeCreator {
	public static Envelope createEnvelope(String report, SecretKey aesKey, PublicKey rsaPublicKey) throws Exception {
		// AES 암호화
		Cipher aesCipher = Cipher.getInstance("AES");
		aesCipher.init(Cipher.ENCRYPT_MODE, aesKey);
		byte[] encryptedReport = aesCipher.doFinal(report.getBytes());

		// RSA로 AES 키 암호화
		Cipher rsaCipher = Cipher.getInstance("RSA");
		rsaCipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
		byte[] encryptedAESKey = rsaCipher.doFinal(aesKey.getEncoded());

		return new Envelope(encryptedReport, encryptedAESKey);
	}
}