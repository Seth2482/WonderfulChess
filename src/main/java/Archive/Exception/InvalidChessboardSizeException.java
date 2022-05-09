package Archive.Exception;

public class InvalidChessboardSizeException extends ArchiveException {
    public InvalidChessboardSizeException(String msg) {
        super(msg);
    }

    public InvalidChessboardSizeException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public InvalidChessboardSizeException(Throwable cause) {
        super(cause);
    }
}
