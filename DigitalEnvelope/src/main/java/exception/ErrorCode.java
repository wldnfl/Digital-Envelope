package exception;

public enum ErrorCode {
    ENCRYPTION_FAIL("암호화에 실패했습니다."),
    DECRYPTION_FAIL("복호화에 실패했습니다."),
    KEY_LOAD_FAIL("키 로드에 실패했습니다."),
    SIGNATURE_FAIL("전자서명 처리에 실패했습니다."),
    KEY_CREATE_FAIL("키 생성에 실패했습니다.");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
