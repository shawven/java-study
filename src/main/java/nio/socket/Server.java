package nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author FS
 * @date 2018/8/30 15:34
 */
public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(8888));
        serverSocketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
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

                if (key.isAcceptable()) {
                    System.out.println("server 接收已就绪");
                    ServerSocketChannel server = (ServerSocketChannel)key.channel();
                    SocketChannel client = server.accept();
                    client.configureBlocking(false);
                    client.register(selector,  SelectionKey.OP_READ);

                    client.write(ByteBuffer.wrap("welcome join us".getBytes()));
                } else if (key.isReadable()) {
                    System.out.println("server 读已就绪");
                    SocketChannel currentChannel = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    currentChannel.read(buffer);
                    buffer.flip();
                    byte[] bytes = new byte[buffer.limit()];
                    while (buffer.hasRemaining()) {
                        buffer.get(bytes);
                    }
                    buffer.clear();
                    System.out.println("已收到:" + new String(bytes, 0, bytes.length));
                } else if (key.isWritable()) {
                    System.out.println("server 写已就绪");
//                    ByteBuffer buffer = ByteBuffer.allocate(1024);
//                    StringBuilder sb = new StringBuilder();
//                    SocketChannel client = (SocketChannel)key.channel();
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
