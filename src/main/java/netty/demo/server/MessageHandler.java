package netty.demo.server;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.demo.common.Message;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 *
 * @description:
 * @author: FS
 * @date: 2018-08-14 11:29
 */
@ChannelHandler.Sharable
public class MessageHandler extends SimpleChannelInboundHandler<Message> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message message) throws Exception {
        System.out.println("接受到数据" + message.toString());
        Message msg = new Message();
        msg.setDate(new Date());
        msg.setData("I'm server, welcome");
        ctx.channel().writeAndFlush(msg).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
    }
}
