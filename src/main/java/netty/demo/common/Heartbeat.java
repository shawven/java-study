package netty.demo.common;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author FS
 * @date 2018/8/14 17:32
 */
public class Heartbeat {

    private static final ByteBuf HEARTBEAT;

    static {
        ByteBuf buffer = Unpooled.buffer(Constants.HEAD_LENGTH);
        buffer.writeByte(Constants.HEARTBEAT);
        buffer.writeByte(Constants.SERVICE_1);
        buffer.writeInt(0);

        HEARTBEAT = Unpooled.wrappedUnmodifiableBuffer(Unpooled.unreleasableBuffer(buffer));
    }

    public static ByteBuf getHeartbeat() {
        return  HEARTBEAT.duplicate();
    }
}
