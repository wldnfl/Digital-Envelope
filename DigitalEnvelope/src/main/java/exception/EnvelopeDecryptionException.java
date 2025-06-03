package exception;

public class EnvelopeDecryptionException extends BaseException {
	public EnvelopeDecryptionException() {
		super(ErrorCode.DECRYPTION_FAIL);
	}
}
