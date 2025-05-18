package exception;

public class KeyCreateException extends BaseException {
	public KeyCreateException() {
		super(ErrorCode.KEY_CREATE_FAIL.getMessage());
	}

}
