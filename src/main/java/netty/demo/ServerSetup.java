package netty.demo;

import netty.demo.server.DefaultSocketServer;
import netty.demo.server.SocketServer;

/**
 * @author FS
 * @date 2018/8/14 16:57
 */
public class ServerSetup {

    public static void main(String[] args) {
        SocketServer socketServer = new DefaultSocketServer(9999);
        socketServer.run();
        socketServer.shutdown();
    }
}
