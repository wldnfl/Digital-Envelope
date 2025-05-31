package exception;

public enum ErrorCode {
	ENCRYPTION_FAIL("암호화에 실패했습니다."),
    DECRYPTION_FAIL("복호화에 실패했습니다."),
    KEY_LOAD_FAIL("키 로드에 실패했습니다."),
    SIGNATURE_FAIL("전자서명 처리에 실패했습니다."),
    KEY_CREATE_FAIL("키 생성에 실패했습니다."),
    KEY_DELETE_FAIL("키 삭제에 실패했습니다."),
    REPORT_NOT_FOUND("해당 고유 코드로 보고 내용을 찾을 수 없습니다."),
    UNIQUE_CODE_EMPTY("고유 코드가 입력되지 않았습니다."),
    EMPTY_REPORT_CONTENT("신고 내용을 입력해주세요."),
    KEY_NOT_EXIST("키가 존재하지 않습니다. 키를 먼저 생성해주세요."),
	ENVELOPE_VERIFICATION("전자봉투 검증 중 오류가 발생했습니다."),
	REPORT_RETRIEVAL_FAIL("신고 목록 조회에 실패했습니다.");

	private final String message;

	ErrorCode(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
