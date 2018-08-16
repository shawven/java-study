package netty.tcp;

import io.netty.channel.CombinedChannelDuplexHandler;

/**
 * Created with IntelliJ IDEA.
 *
 * @description:
 * @author: FS
 * @date: 2018-08-09 16:48
 */
public class MessageCodec extends CombinedChannelDuplexHandler<MessageDecoder, MessageEncoder> {

    public MessageCodec() {
        super(new MessageDecoder(), new MessageEncoder());
    }
}
