package netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Created with IntelliJ IDEA.
 *
 * @description:
 * @author: FS
 * @date: 2018-08-09 16:45
 */
public class MessageEncoder extends MessageToByteEncoder {

    public static final Byte SEPARATOR = '\n';

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        Message message = (Message) o;

        byteBuf.writeInt(message.getLength());
        byteBuf.writeCharSequence(message.getText(), StandardCharsets.UTF_8);
    }
}
