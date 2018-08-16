package netty.demo.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import netty.demo.common.Constants;
import netty.demo.common.Message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created with IntelliJ IDEA.
 *
 * @description:
 * @author: FS
 * @date: 2018-08-14 11:21
 */
@ChannelHandler.Sharable
public class MessageEncoder extends MessageToByteEncoder<Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message message, ByteBuf byteBuf) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(message);
        objectOutputStream.flush();
        byte[] bytes = byteArrayOutputStream.toByteArray();

        byteBuf.writeByte(Constants.RESPONSE);
        byteBuf.writeByte(Constants.SERVICE_1);
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes( byteArrayOutputStream.toByteArray());
    }
}
