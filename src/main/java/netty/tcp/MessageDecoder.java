package netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @description:
 * @author: FS
 * @date: 2018-08-09 16:47
 */
public class MessageDecoder extends ByteToMessageDecoder {

    public static final Byte SEPARATOR = '\n';

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        int index = byteBuf.indexOf(byteBuf.readerIndex(), byteBuf.writerIndex(), SEPARATOR);

        ByteBuf lengthByteBuf = byteBuf.slice(byteBuf.readerIndex(), index);

        int length = byteBuf.readInt();
        String text = byteBuf.slice(lengthByteBuf.readableBytes(), byteBuf.writerIndex()).toString();

        list.add(new Message(length, text));
    }
}
