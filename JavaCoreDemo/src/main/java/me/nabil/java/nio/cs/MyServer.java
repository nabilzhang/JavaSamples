package me.nabil.java.nio.cs;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 基于NIO的服务
 *
 * @author 服务
 */
public class MyServer {

    /**
     * 该SERVER的端口
     */
    private int port = 6789;

    ServerSocketChannel ssc = null;

    Selector selector = null;

    private ByteBuffer send = ByteBuffer.allocate(1024);
    private ByteBuffer receive = ByteBuffer.allocate(1024);

    /**
     * 保存channel 和读到数据的date，用于做超时
     */
    private Map<SocketChannel, Date> lastReadDate = new HashMap<>();
    /**
     * 超时3秒
     */
    private int timeOut = 3;


    /**
     * construct
     *
     * @param port 端口
     */
    public MyServer(int port) {
        this.port = port;
    }

    /**
     * 默认
     */
    public MyServer() {
    }

    /**
     * 启动
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {
        MyServer server = new MyServer();
        server.start();
        server.listen();
    }

    /**
     * 启动服务
     */
    private void start() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(port));
        selector = Selector.open();


        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("system start, listening:" + port);
        send.put("data come from server".getBytes());
    }

    /**
     * 监听
     *
     * @throws IOException
     */
    private void listen() throws IOException {
        while (true) {
            // 选择一组键，并且相应的通道已经打开
            selector.select();
            // 返回此选择器的已选择键集。
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();

                // 这里记得手动的把他remove掉，不然selector中的selectedKeys集合不会自动去除
                iterator.remove();
                dealKey(selectionKey);
            }
        }
    }


    /**
     * 处理请求
     *
     * @param selectionKey
     * @throws IOException
     */
    private void dealKey(SelectionKey selectionKey) throws IOException {

        ServerSocketChannel server = null;
        SocketChannel client = null;
        String receiveText;
        String sendText;
        int count = 0;

        // 测试此键的通道是否已准备好接受新的套接字连接。
        if (selectionKey.isAcceptable()) {
            // 返回为之创建此键的通道。
            server = (ServerSocketChannel) selectionKey.channel();

            // 此方法返回的套接字通道（如果有）将处于阻塞模式。
            client = server.accept();
            // 配置为非阻塞
            client.configureBlocking(false);
            // 注册到selector，等待连接
            client.register(selector, SelectionKey.OP_READ);
            updateLastCommunicateDate(client);
        } else if (selectionKey.isReadable()) {
            // 返回为之创建此键的通道。
            client = (SocketChannel) selectionKey.channel();
            // 将缓冲区清空以备下次读取
            receive.clear();
            // 读取服务器发送来的数据到缓冲区中
            count = client.read(receive);
            if (count > 0) {
                updateLastCommunicateDate(client);
                receiveText = new String(receive.array(), 0, count);
                System.out.println("服务器端接受客户端数据--:" + receiveText);
                selectionKey.interestOps(SelectionKey.OP_WRITE | SelectionKey.OP_READ);

            } else {
                if (isTimeOut(client)) {
                    // 这里读不到数据就关闭，感觉有点草率，需要超时控制
                    System.out.println("the client " + client + " is closed");
                    client.close();
                    lastReadDate.remove(client);
                }
            }

        } else if (selectionKey.isWritable()) {
            // 将缓冲区清空以备下次写入
            send.flip();
            // 返回为之创建此键的通道。
            client = (SocketChannel) selectionKey.channel();

            // 输出到通道
            count = client.write(send);

            if (count > 0) {
                updateLastCommunicateDate(client);
            }

            // 如果没写完可以考虑interest
            if (send.position() < count) {
                selectionKey.interestOps(SelectionKey.OP_WRITE);
            } else {
                selectionKey.interestOps(SelectionKey.OP_READ);
            }

        }
    }

    private void updateLastCommunicateDate(SocketChannel channel) {
        this.lastReadDate.put(channel, new Date());
    }

    /**
     * 是否已经超时
     *
     * @param channel
     * @return
     */
    private boolean isTimeOut(SocketChannel channel) {
        Date lastDate = this.lastReadDate.get(channel);
        if (lastDate == null) {
            return false;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, -timeOut);
        if (lastDate.before(calendar.getTime())) {
            return true;
        }
        return false;
    }

}
