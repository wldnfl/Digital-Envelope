package exception;

public class DecryptionException extends BaseException {
	public DecryptionException() {
		super(ErrorCode.DECRYPTION_FAIL.getMessage());
	}
}
