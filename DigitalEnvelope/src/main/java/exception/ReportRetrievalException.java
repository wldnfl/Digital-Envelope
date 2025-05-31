package exception;

public class ReportRetrievalException extends BaseException {
	public ReportRetrievalException() {
		super(ErrorCode.REPORT_RETRIEVAL_FAIL);
	}
}
