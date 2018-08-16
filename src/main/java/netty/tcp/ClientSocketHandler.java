package netty.tcp;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * Created with IntelliJ IDEA.
 *
 * @description:
 * @author: FS
 * @date: 2018-08-09 17:38
 */
public class ClientSocketHandler extends SimpleChannelInboundHandler<Message> {

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client inactive");
        super.channelInactive(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client active");
        super.channelActive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        super.exceptionCaught(ctx, cause);
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message message) throws Exception {

        ctx.writeAndFlush(new Message("你好，我是客服端"));
    }
}
