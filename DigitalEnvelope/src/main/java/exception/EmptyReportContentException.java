package exception;

public class EmptyReportContentException extends BaseException {
	public EmptyReportContentException() {
		super(ErrorCode.EMPTY_REPORT_CONTENT);
	}
}