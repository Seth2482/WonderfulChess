package Archive.Exception;

import com.google.gson.JsonParseException;

public class InvalidChessTypeException extends ArchiveException {
    public InvalidChessTypeException(String msg) {
        super(msg);
    }

    public InvalidChessTypeException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public InvalidChessTypeException(Throwable cause) {
        super(cause);
    }
}
