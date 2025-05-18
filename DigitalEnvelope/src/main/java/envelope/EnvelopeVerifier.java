package envelope;

import util.CryptoUtil;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;

public class EnvelopeVerifier {
	public static boolean verifyEnvelope(Envelope envelope, PrivateKey receiverPrivKey, PublicKey senderPubKey)
			throws Exception {
		byte[] aesKeyBytes = CryptoUtil.decryptRSA(envelope.encryptedKey, receiverPrivKey);
		SecretKey aesKey = new SecretKeySpec(aesKeyBytes, "AES");
		byte[] data = CryptoUtil.decryptAES(envelope.encryptedData, aesKey);
		return CryptoUtil.verifySignature(data, envelope.signature, senderPubKey);
	}

	public static String decryptEnvelopeData(Envelope envelope, PrivateKey receiverPrivKey) throws Exception {
		byte[] aesKeyBytes = CryptoUtil.decryptRSA(envelope.encryptedKey, receiverPrivKey);
		SecretKey aesKey = new SecretKeySpec(aesKeyBytes, "AES");
		byte[] data = CryptoUtil.decryptAES(envelope.encryptedData, aesKey);
		return new String(data);
	}
}
