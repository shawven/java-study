package netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created with IntelliJ IDEA.
 *
 * @description:
 * @author: FS
 * @date: 2018-07-25 17:36
 */
public class Runner {
    public static void main(String[] args) {
        EventLoopGroup eventExecutors = new NioEventLoopGroup();

        ServerBootstrap boot = new ServerBootstrap();

        try {
            boot.group(eventExecutors)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new WebSocketChannelHandler());

            System.out.println("服务开启中...");

            boot.bind(8888).sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
