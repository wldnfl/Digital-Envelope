package exception;

public class KeyLoadException extends BaseException {
	public KeyLoadException() {
		super(ErrorCode.KEY_LOAD_FAIL.getMessage());
	}
}
