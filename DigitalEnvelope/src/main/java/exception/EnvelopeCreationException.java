package exception;

public class EnvelopeCreationException extends BaseException {
	public EnvelopeCreationException() {
		super(ErrorCode.ENVELOPE_CREATE_FAIL);
	}

	public EnvelopeCreationException(Throwable cause) {
		super(ErrorCode.ENVELOPE_CREATE_FAIL, cause);
	}
}
