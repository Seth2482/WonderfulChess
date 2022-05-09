package Archive.Exception;

public class InvalidStepException extends ArchiveException {
    public InvalidStepException(String msg) {
        super(msg);
    }

    public InvalidStepException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public InvalidStepException(Throwable cause) {
        super(cause);
    }
}
