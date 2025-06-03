package util;

import java.util.Base64;

public class Base64Util {

	// byte[] → Base64 인코딩 문자열
	public static String encode(byte[] data) {
		return Base64.getEncoder().encodeToString(data);
	}

	// Base64 문자열 → byte[]
	public static byte[] decode(String base64Str) {
		return Base64.getDecoder().decode(base64Str);
	}
}
