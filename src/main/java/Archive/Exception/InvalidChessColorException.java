package Archive.Exception;

import com.google.gson.JsonParseException;

public class InvalidChessColorException extends ArchiveException {
    public InvalidChessColorException(String msg) {
        super(msg);
    }

    public InvalidChessColorException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public InvalidChessColorException(Throwable cause) {
        super(cause);
    }
}
