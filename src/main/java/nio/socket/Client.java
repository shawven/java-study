package nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author FS
 * @date 2018/8/30 15:34
 */
public class Client {

    public static void main(String[] args) throws IOException {
        SocketChannel clientSocketChannel = SocketChannel.open();
        clientSocketChannel.socket().connect(new InetSocketAddress(8888));
        clientSocketChannel.configureBlocking(false);

        Selector selector = Selector.open();

        clientSocketChannel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ);

        while (clientSocketChannel.finishConnect()) {

            try {
                int selects = selector.select();
                if (selects == 0) {
                    continue;
                }
            } catch (IOException e) {
                continue;
            }

            Iterator<SelectionKey> it = selector.selectedKeys().iterator();

            while (it.hasNext()) {
                SelectionKey key = it.next();
                it.remove();

                SocketChannel client = (SocketChannel) key.channel();

                if (key.isConnectable()) {
                    System.out.println("client 连接已就绪");

                } else if (key.isReadable()) {
                    System.out.println("client 读已就绪");
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    StringBuilder sb = new StringBuilder();
                    while (client.read(buffer) != -1) {
                        buffer.flip();
                        sb.append(buffer.get());
                        buffer.clear();
                    }
                    System.out.println("已收到:" + sb.toString());

                    ScheduledExecutorService scheduledExecutorService =
                            Executors.newScheduledThreadPool(1, Thread::new);
                    scheduledExecutorService.scheduleWithFixedDelay(() -> {
                        System.out.println("即将发送数据");
                        try {
                            client.write(ByteBuffer.wrap("第一次主动发送数据".getBytes()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }, 0, 5 , TimeUnit.SECONDS);

                } else if (key.isWritable()) {
                    System.out.println("client 写已就绪");
//                    ByteBuffer buffer = ByteBuffer.allocate(1024);
//                    StringBuilder sb = new StringBuilder();
//                    while (client.read(buffer) != -1) {
//                        buffer.flip();
//                        sb.append(buffer.get());
//                    }
//                    System.out.println("写了:" + sb.toString());
                }
            }
        }
    }
}
