package exception;

public class EnvelopeVerificationException extends BaseException {

	public EnvelopeVerificationException() {
		super(ErrorCode.ENVELOPE_VERIFICATION);
	}

	public EnvelopeVerificationException(String detailMessage) {
		super(ErrorCode.ENVELOPE_VERIFICATION, detailMessage);
	}
}
