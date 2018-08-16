package netty.demo.server;

/**
 * Created with IntelliJ IDEA.
 *
 * @description:
 * @author: FS
 * @date: 2018-08-14 10:24
 */
public interface SocketServer {

    /**
     *  server启动方法
     */
    void run();

    /**
     *  server 关闭方法
     */
    void shutdown();
}
