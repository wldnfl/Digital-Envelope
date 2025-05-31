package exception;

public class UniqueCodeEmptyException extends BaseException {
    public UniqueCodeEmptyException() {
        super(ErrorCode.UNIQUE_CODE_EMPTY);
    }
}