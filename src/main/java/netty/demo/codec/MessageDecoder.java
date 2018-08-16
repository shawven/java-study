package netty.demo.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import netty.demo.common.Constants;
import netty.demo.common.Message;
import netty.demo.common.ProtocolHeader;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.List;

import static netty.demo.common.Constants.HEARTBEAT;
import static netty.demo.common.Constants.RESPONSE;
import static netty.demo.common.Constants.SERVICE_1;

/**
 * @author FS
 * @date 2018-08-14 13:33
 */
public class MessageDecoder extends ReplayingDecoder<MessageDecoder.State> {

    private ProtocolHeader protocol = new ProtocolHeader();

    public MessageDecoder() {
        super(State.READ_TYPE);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        // 一直往下执行
        switch (state()) {
            case READ_TYPE:
                protocol.setType(byteBuf.readByte());
                checkpoint(State.READ_SERVER);
            case READ_SERVER:
                protocol.setServer(byteBuf.readByte());
                checkpoint(State.READ_BODY_LENGTH);
            case READ_BODY_LENGTH:
                protocol.setBodyLength(byteBuf.readInt());
                checkpoint(State.READ_BODY);
            case READ_BODY:

                if (protocol.getType() == HEARTBEAT) {
                    break;
                }

                switch (protocol.getServer()) {
                    case RESPONSE:
                    case SERVICE_1:
                        byte[] contentBytes = new byte[protocol.getBodyLength()];
                        byteBuf.readBytes(contentBytes);

                        ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(contentBytes));
                        list.add((Message)objectInputStream.readObject());
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
            default:
        }
        checkpoint(State.READ_TYPE);
    }

    public enum State {
        /**
         * 读取消息类型
         */
        READ_TYPE,

        /**
         * 读取响应服务器
         */
        READ_SERVER,

        /**
         * 读取字节长度
         */
        READ_BODY_LENGTH,


        /**
         * 读取字节内容
         */
        READ_BODY
    }
}
