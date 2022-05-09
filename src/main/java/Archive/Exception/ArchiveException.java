package Archive.Exception;

import com.google.gson.JsonParseException;

public class ArchiveException extends JsonParseException {
    public ArchiveException(String msg) {
        super(msg);
    }

    public ArchiveException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ArchiveException(Throwable cause) {
        super(cause);
    }
}
