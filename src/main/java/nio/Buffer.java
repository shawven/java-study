package nio;

import io.netty.buffer.ByteBuf;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author FS
 * @date 2018/8/27 10:57
 */
public class Buffer {

    public static void main(String[] args) throws IOException {
        String oldPath = "D:\\WorkSpace\\java\\Test\\src\\main\\java\\nio\\a.txt";
        String newPath = "D:\\WorkSpace\\java\\Test\\src\\main\\java\\nio\\b.txt";

        File file = new File(newPath);
        if (!file.exists()) {
            boolean b = file.createNewFile();
        }

        FileChannel oldChannel = new RandomAccessFile(oldPath, "rw").getChannel();
        FileChannel newChannel = new RandomAccessFile(newPath, "rw").getChannel();

        oldChannel.position(3);
        newChannel.transferFrom(oldChannel, 3, oldChannel.size());


        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
//
        while (oldChannel.read(byteBuffer) != -1) {

            byteBuffer.flip();

            newChannel.write(byteBuffer);

            byteBuffer.clear();
        }
        oldChannel.close();
        newChannel.force(true);
        newChannel.close();
    }


}
