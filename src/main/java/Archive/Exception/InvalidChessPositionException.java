package Archive.Exception;

import com.google.gson.JsonParseException;

public class InvalidChessPositionException extends ArchiveException {
    public InvalidChessPositionException(String msg) {
        super(msg);
    }

    public InvalidChessPositionException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public InvalidChessPositionException(Throwable cause) {
        super(cause);
    }
}
