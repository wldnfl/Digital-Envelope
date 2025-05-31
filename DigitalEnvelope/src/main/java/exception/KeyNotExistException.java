package exception;

public class KeyNotExistException extends BaseException {
	public KeyNotExistException() {
		super(ErrorCode.KEY_NOT_EXIST);
	}
}