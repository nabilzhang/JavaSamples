package me.nabil.sample.reactordemo.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;

/**
 * @author zhangbi
 * 
 */
public class ReactorDemoClient implements Runnable {
	private InetAddress hostAddress;
	private int port;
	private Selector selector;
	private ByteBuffer readBuffer = ByteBuffer.allocate(8192);
	private ByteBuffer outBuffer = ByteBuffer.wrap("nice to meet you"
			.getBytes());

	public ReactorDemoClient(InetAddress hostAddress, int port)
			throws IOException {
		this.hostAddress = hostAddress;
		this.port = port;
		initSelector();
	}

	public static void main(String[] args) {
		try {
			ReactorDemoClient client = new ReactorDemoClient(
					InetAddress.getLocalHost(), 8090);
			new Thread(client).start();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			try {
				selector.select();

				Iterator<?> selectedKeys = selector.selectedKeys().iterator();
				while (selectedKeys.hasNext()) {
					SelectionKey key = (SelectionKey) selectedKeys.next();
					selectedKeys.remove();

					if (!key.isValid()) {
						continue;
					}

					if (key.isConnectable()) {
						finishConnection(key);
					} else if (key.isReadable()) {
						read(key);
					} else if (key.isWritable()) {
						write(key);
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private void initSelector() throws IOException {
		// ����һ��selector
		selector = SelectorProvider.provider().openSelector();
		// ��SocketChannel
		SocketChannel socketChannel = SocketChannel.open();
		// ����Ϊ������
		socketChannel.configureBlocking(false);
		// ����ָ��IP�Ͷ˿ڵĵ�ַ
		socketChannel
				.connect(new InetSocketAddress(this.hostAddress, this.port));
		// ��selectorע���׽��֣������ض�Ӧ��SelectionKey��ͬʱ����Key��interest setΪ����������ѽ������ӵ��¼�
		socketChannel.register(selector, SelectionKey.OP_CONNECT);
	}

	private void finishConnection(SelectionKey key) throws IOException {
		SocketChannel socketChannel = (SocketChannel) key.channel();
		try {
			// �ж������Ƿ����ɹ������ɹ������쳣
			socketChannel.finishConnect();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			key.cancel();
			return;
		}
		// ����Key��interest setΪOP_WRITE�¼�
		key.interestOps(SelectionKey.OP_WRITE);
	}

	/**
	 * ����read
	 * 
	 * @param key
	 * @throws IOException
	 */
	private void read(SelectionKey key) throws IOException {
		SocketChannel socketChannel = (SocketChannel) key.channel();
		readBuffer.clear();
		int numRead;
		try {
			numRead = socketChannel.read(readBuffer);
		} catch (Exception e) {
			key.cancel();
			socketChannel.close();
			return;
		}
		if (numRead == 1) {
			System.out.println("close connection");
			socketChannel.close();
			key.cancel();
			return;
		}
		// ������Ӧ
		handleResponse(socketChannel, readBuffer.array(), numRead);
	}

	/**
	 * ������Ӧ
	 * 
	 * @param socketChannel
	 * @param data
	 * @param numRead
	 * @throws IOException
	 */
	private void handleResponse(SocketChannel socketChannel, byte[] data,
			int numRead) throws IOException {
		byte[] rspData = new byte[numRead];
		System.arraycopy(data, 0, rspData, 0, numRead);
		System.out.println(new String(rspData));
		socketChannel.close();
		socketChannel.keyFor(selector).cancel();
	}

	/**
	 * ����write
	 * 
	 * @param key
	 * @throws IOException
	 */
	private void write(SelectionKey key) throws IOException {
		SocketChannel socketChannel = (SocketChannel) key.channel();
		socketChannel.write(outBuffer);
		if (outBuffer.remaining() > 0) {
			return;
		}
		String outStr = new String(outBuffer.array());
		System.out.println(outBuffer.toString() + outStr);
		// ����Key��interest setΪOP_READ�¼�
		key.interestOps(SelectionKey.OP_WRITE);
	}

}
