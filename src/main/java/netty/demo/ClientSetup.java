package netty.demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import netty.demo.codec.MessageDecoder;
import netty.demo.codec.MessageEncoder;
import netty.demo.common.Heartbeat;
import netty.demo.common.Message;

import java.net.SocketAddress;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author FS
 * @date 2018/8/14 16:57
 */
public class ClientSetup {

    public static void main(String[] args) {
        EventLoopGroup worker = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(worker)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.ALLOW_HALF_CLOSURE, false)
                .option(ChannelOption.MESSAGE_SIZE_ESTIMATOR, DefaultMessageSizeEstimator.DEFAULT)
                .option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, (int) SECONDS.toMillis(3))
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(
                            new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS),
                            new ClientIdleStateTrigger(),
                            new MessageDecoder(),
                            new MessageEncoder(),
                            new ClientConnectHandler(),
                            new ClientMessageHandler()
                        );
                    }
                });

        ChannelFuture channelFuture = null;
        try {
            channelFuture = bootstrap.connect("127.0.0.1", 9999).addListener(future -> {
                if (future.isSuccess()) {
                    System.out.println("客户端已启动");
                }
            }).sync();

            channelFuture.channel().writeAndFlush(new Message(){{
                setDate(new Date());
                setData("hello I'm client");
            }});
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @ChannelHandler.Sharable
    static class ClientIdleStateTrigger extends ChannelInboundHandlerAdapter {
        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (evt instanceof IdleStateEvent) {
                IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
                if (idleStateEvent.state() == IdleState.WRITER_IDLE) {
                    ctx.channel().writeAndFlush(Heartbeat.getHeartbeat());
                }
            }

            super.userEventTriggered(ctx, evt);
        }
    }

    @ChannelHandler.Sharable
    static class ClientMessageHandler extends SimpleChannelInboundHandler<Message> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Message message) throws Exception {
            System.out.println("I'm client received:" + message.getData());
        }
    }

    @ChannelHandler.Sharable
    static class ClientConnectHandler extends ChannelOutboundHandlerAdapter {
        @Override
        public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
            System.out.println("处理器检测已连接到：" + remoteAddress.toString());
            super.connect(ctx, remoteAddress, localAddress, promise);
        }

        @Override
        public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
            promise.addListener(future -> {
                System.out.println("处理器检测到已断开连接");
            });
            super.disconnect(ctx, promise);
        }

        @Override
        public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
            System.out.println("处理器检测到连接已关闭");
            super.close(ctx, promise);
        }

        @Override
        public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
            System.out.println("处理器检测正在绑定端口：" + localAddress.toString());
            super.bind(ctx, localAddress, promise);
        }
    }
}
