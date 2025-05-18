package exception;

public enum ErrorCode {
    ENCRYPTION_FAIL(400, "암호화에 실패했습니다."),
    DECRYPTION_FAIL(400, "복호화에 실패했습니다."),
    KEY_LOAD_FAIL(400, "키 로드에 실패했습니다."),
    SIGNATURE_FAIL(400, "전자서명 처리에 실패했습니다.");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
