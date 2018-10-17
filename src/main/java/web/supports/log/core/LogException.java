package web.supports.log.core;

/**
 * Created with IntelliJ IDEA.
 *
 * @description:
 * @author: FS
 * @date: 2018-03-26 10:26
 */
public class LogException extends RuntimeException {

    public LogException() {
    }

    public LogException(String message) {
        super(message);
    }

    public LogException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogException(Throwable cause) {
        super(cause);
    }
}
