package envelope;

import util.CryptoUtil;
import model.Report;
import javax.crypto.SecretKey;
import java.security.*;

public class EnvelopeCreator {
	public static Envelope createEnvelope(Report report, PublicKey receiverPubKey, PrivateKey senderPrivKey,
			SecretKey aesKey) throws Exception {
		byte[] reportBytes = (report.getTitle() + "\n" + report.getContent()).getBytes();
		byte[] encryptedData = CryptoUtil.encryptAES(reportBytes, aesKey);
		byte[] signature = CryptoUtil.signData(reportBytes, senderPrivKey);
		byte[] encryptedKey = CryptoUtil.encryptRSA(aesKey.getEncoded(), receiverPubKey);
		return new Envelope(encryptedData, encryptedKey, signature);
	}
}