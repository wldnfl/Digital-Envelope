package util;

import java.security.*;
import java.util.Base64;

public class SignatureUtil {
	public static String generateSignature(String data, PrivateKey privateKey) throws Exception {
		Signature sign = Signature.getInstance("SHA256withRSA");
		sign.initSign(privateKey);
		sign.update(data.getBytes());
		return Base64.getEncoder().encodeToString(sign.sign());
	}

	public static boolean verifySignature(String data, String signature, PublicKey publicKey) throws Exception {
		Signature sign = Signature.getInstance("SHA256withRSA");
		sign.initVerify(publicKey);
		sign.update(data.getBytes());
		byte[] sigBytes = Base64.getDecoder().decode(signature);
		return sign.verify(sigBytes);
	}
}