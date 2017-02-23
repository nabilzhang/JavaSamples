package me.nabil.java.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * socketDemo
 *
 * @author zhangbi
 */
public class SocketChannelDemo {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();

        socketChannel.connect(new InetSocketAddress("ifeve.com", 80));

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        socketChannel.read(byteBuffer);
        while (byteBuffer.hasRemaining()) {
            byteBuffer.flip();

            System.out.println(byteBuffer.asCharBuffer().toString());

            byteBuffer.clear();
            socketChannel.read(byteBuffer);
        }

    }
}
