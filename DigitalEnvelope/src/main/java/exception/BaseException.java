package exception;

public class BaseException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private final ErrorCode errorCode;

	public BaseException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

	// 상세 메시지를 받을 수 있는 생성자 추가
	public BaseException(ErrorCode errorCode, String detailMessage) {
		super(errorCode.getMessage() + ": " + detailMessage);
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}
}
