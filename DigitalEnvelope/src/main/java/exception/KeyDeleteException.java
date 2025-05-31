package exception;

public class KeyDeleteException extends BaseException {
	public KeyDeleteException() {
		super(ErrorCode.KEY_DELETE_FAIL);
	}
}
