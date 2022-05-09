package Archive.Exception;

public class DuplicatedChessException extends ArchiveException {
    public DuplicatedChessException(String msg) {
        super(msg);
    }

    public DuplicatedChessException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public DuplicatedChessException(Throwable cause) {
        super(cause);
    }
}
