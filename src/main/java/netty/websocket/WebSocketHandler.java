package netty.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.nio.charset.StandardCharsets;

/**
 * Created with IntelliJ IDEA.
 *
 * @description:
 * @author: FS
 * @date: 2018-07-25 16:28
 */
public class WebSocketHandler extends SimpleChannelInboundHandler<Object> {

    private static final String WEB_SOCKET_URL  = "ws://localhost:8888/ws";

    private static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private WebSocketServerHandshaker handshaker;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {


        group.add(ctx.channel());
        System.out.println("客户端与服务端建立连接...");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        group.writeAndFlush(new TextWebSocketFrame("已关闭"));
        group.remove(ctx.channel());
        System.out.println("客户端与服务端连接关闭...");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext context, Object request) throws Exception {
        if (request instanceof FullHttpRequest) {
            handleWebSocketConnect(context, (FullHttpRequest) request);
        }

        if (request instanceof WebSocketFrame) {
            handleWebSocketFrame(context, (WebSocketFrame) request);
        }
    }

    /**
     * @param ctx
     * @param frame
     */
    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        Channel channel = ctx.channel();

        if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(channel, (CloseWebSocketFrame) frame.retain());
            return;
        }

        if (frame instanceof PingWebSocketFrame) {
            channel.writeAndFlush(new PingWebSocketFrame());
            return;
        }

        if (frame instanceof TextWebSocketFrame) {
            channel.writeAndFlush(new TextWebSocketFrame("已收到：" + ((TextWebSocketFrame) frame).text()));
        }
    }

    /**
     * @param ctx
     * @param request
     */
    private void handleWebSocketConnect(ChannelHandlerContext ctx, FullHttpRequest request) {
        Channel channel = ctx.channel();

        if (!(request.decoderResult().isSuccess()
                && "websocket".equals(request.headers().get("Upgrade")))) {

            DefaultFullHttpResponse response =
                    new DefaultFullHttpResponse(HttpVersion.HTTP_1_0, HttpResponseStatus.NOT_ACCEPTABLE);

            ByteBuf rspBody = Unpooled.copiedBuffer(response.status().toString(), StandardCharsets.UTF_8);

            response.content().writeBytes(rspBody);

            channel.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        }

        handshaker = new WebSocketServerHandshakerFactory(WEB_SOCKET_URL, null, false).newHandshaker(request);

        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(channel);
        }

        handshaker.handshake(channel, request);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
