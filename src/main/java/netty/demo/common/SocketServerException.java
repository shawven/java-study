package netty.demo.common;

/**
 * @author FS
 * @date 2018/8/14 16:51
 */
public class SocketServerException extends RuntimeException {

    public SocketServerException() {
        super();
    }

    public SocketServerException(String message) {
        super(message);
    }

    public SocketServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public SocketServerException(Throwable cause) {
        super(cause);
    }


}
