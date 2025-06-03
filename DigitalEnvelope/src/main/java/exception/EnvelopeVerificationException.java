package exception;

public class EnvelopeVerificationException extends BaseException {

	public EnvelopeVerificationException() {
		super(ErrorCode.ENVELOPE_VERIFICATION);
	}

	public EnvelopeVerificationException(Throwable casue) {
		super(ErrorCode.ENVELOPE_VERIFICATION);
	}
}
