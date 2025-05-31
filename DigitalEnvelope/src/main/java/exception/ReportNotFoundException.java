package exception;

public class ReportNotFoundException extends BaseException {
	public ReportNotFoundException() {
		super(ErrorCode.REPORT_NOT_FOUND);
	}
}
