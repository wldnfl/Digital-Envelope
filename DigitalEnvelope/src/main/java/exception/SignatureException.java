package exception;

public class SignatureException extends BaseException {
	public SignatureException() {
		super(ErrorCode.SIGNATURE_FAIL.getMessage());
	}
}
