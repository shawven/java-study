package netty.tcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created with IntelliJ IDEA.
 *
 * @description:
 * @author: FS
 * @date: 2018-08-09 16:30
 */
public class ServerSocketHandler extends SimpleChannelInboundHandler<Message> {

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("server inactive");
        super.channelInactive(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("server active");

        super.channelActive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message message)
            throws Exception {
        ctx.writeAndFlush(new Message("你好，我是服务端"));
    }
}
