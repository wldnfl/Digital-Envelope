package util;

import java.security.*;
import java.util.Base64;

public class SignatureUtil {

	public static byte[] sign(byte[] data, PrivateKey privateKey) throws Exception {
		Signature signature = Signature.getInstance("SHA256withRSA");
		signature.initSign(privateKey);
		signature.update(data);
		return signature.sign();
	}

	public static boolean verify(byte[] data, byte[] signatureBytes, PublicKey publicKey) throws Exception {
		Signature signature = Signature.getInstance("SHA256withRSA");
		signature.initVerify(publicKey);
		signature.update(data);
		return signature.verify(signatureBytes);
	}

	public static String signToBase64(byte[] data, PrivateKey privateKey) throws Exception {
		byte[] signatureBytes = sign(data, privateKey);
		return Base64.getEncoder().encodeToString(signatureBytes);
	}

	public static boolean verifyFromBase64(byte[] data, String signatureBase64, PublicKey publicKey) throws Exception {
		byte[] signatureBytes = Base64.getDecoder().decode(signatureBase64);
		return verify(data, signatureBytes, publicKey);
	}
}
