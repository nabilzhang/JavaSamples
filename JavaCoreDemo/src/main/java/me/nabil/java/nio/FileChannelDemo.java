package me.nabil.java.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * file channel demo
 *
 * @author zhangbi
 */
public class FileChannelDemo {

    private static String RESOURCE_DIR = System.getProperty("user.dir") + "/resources/";

    public static void main(String[] args) throws IOException {
        // 1. basic
        basicChannel();

        // 2. from to
        fromChannelToChannel();
    }


    /**
     * 从一个channel 到channel
     */
    private static void fromChannelToChannel() throws IOException {
        RandomAccessFile fromFile = new RandomAccessFile(RESOURCE_DIR + "data/fromFile.txt", "rw");
        FileChannel fromChannel = fromFile.getChannel();

        RandomAccessFile toFile = new RandomAccessFile(RESOURCE_DIR + "data/toFile.txt", "rw");
        FileChannel toChannel = toFile.getChannel();

        long position = 0;
        long count = fromChannel.size();
        toChannel.transferFrom(fromChannel, position, count);
    }


    /**
     * 基本
     *
     * @throws IOException
     */
    private static void basicChannel() throws IOException {
        RandomAccessFile aFile = new RandomAccessFile(RESOURCE_DIR
                + "data/nio-data.txt", "rw");
        FileChannel inChannel = aFile.getChannel();

        ByteBuffer buf = ByteBuffer.allocate(48);

        System.out.println("[buf capacity]: " + buf.capacity() +
                "[buf position]: " + buf.position() +
                "[buf limit]: " + buf.limit());

        int bytesRead = inChannel.read(buf);

        System.out.println("[buf capacity]: " + buf.capacity() +
                "[buf position]: " + buf.position() +
                "[buf limit]: " + buf.limit());

        while (bytesRead != -1) {
            System.out.println("[read]: " + bytesRead);

            System.out.println("[buf capacity]: " + buf.capacity() +
                    "[buf position]: " + buf.position() +
                    "[buf limit]: " + buf.limit());
            System.out.println("[buf capacity] " + buf.capacity());

            buf.flip();

            System.out.println("[buf capacity]: " + buf.capacity() +
                    "[buf position]: " + buf.position() +
                    "[buf limit]: " + buf.limit());

            while (buf.hasRemaining()) {
                System.out.println("[buf capacity]: " + buf.capacity() +
                        "[buf position]: " + buf.position() +
                        "[buf limit]: " + buf.limit());
                System.out.print((char) buf.get());
            }

            System.out.println("[buf capacity]: " + buf.capacity() +
                    "[buf position]: " + buf.position() +
                    "[buf limit]: " + buf.limit());
            buf.clear();
            System.out.println("[buf capacity]: " + buf.capacity() +
                    "[buf position]: " + buf.position() +
                    "[buf limit]: " + buf.limit());
            bytesRead = inChannel.read(buf);
        }

        aFile.close();
    }

}
