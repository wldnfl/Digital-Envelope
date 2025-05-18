package exception;

public class EncryptionException extends BaseException {
    public EncryptionException() {
        super(ErrorCode.ENCRYPTION_FAIL.getMessage());
    }
}
