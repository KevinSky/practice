package kevin.practice.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {

	ServerSocketChannel serverSocketChannel;
	private ServerSocket serverSocket_;
	Selector acceptSelector;
	private boolean stopped_ = false;
	private AtomicInteger count;

	private AtomicInteger change;

	public void start() {
		this.listen();
		System.out.println("成功启动，开始监听");
		while (true) {
			this.accept();
		}
	}

	public void listen() {
		try {
			count = new AtomicInteger(0);
			change = new AtomicInteger(0);
			InetSocketAddress bindAddr = new InetSocketAddress(8889);
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(false);
			// Make server socket
			serverSocket_ = serverSocketChannel.socket();
			// Prevent 2MSL delay problem on server restarts
			serverSocket_.setReuseAddress(true);
			// Bind to listening port
			serverSocket_.bind(bindAddr);

			acceptSelector = SelectorProvider.provider().openSelector();
			serverSocketChannel
					.register(acceptSelector, SelectionKey.OP_ACCEPT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void accept() {

		try {
			acceptSelector.select();
			Iterator<SelectionKey> selectedKeys = acceptSelector.selectedKeys()
					.iterator();
			int size = change.getAndIncrement();
			System.out.println("变化 了：" + size);
			while (!stopped_ && selectedKeys.hasNext()) {
				SelectionKey key = selectedKeys.next();
				selectedKeys.remove();
				// skip if not valid
				if (!key.isValid()) {
					continue;
				}
				if (key.isAcceptable()) {
					handleAccept(key);
				} else if (key.isReadable()) {
					handleRead(key);
				} if (key.isWritable()) {
					handleWrite(key);
				} else {
					System.out.println("Unexpected state in select! "
							+ key.interestOps());
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void handleAccept(SelectionKey key) {
		try {
			System.out.println("有新的来了,已有：" + count.get());
			SocketChannel channel = ((ServerSocketChannel) key.channel())
					.accept();
			channel.configureBlocking(false);
			channel.register(key.selector(), SelectionKey.OP_READ);
			System.out.println("成功接受了这家伙，已有：" + count.get());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void handleRead(SelectionKey key) {
		try {
			SocketChannel socketChannel = (SocketChannel) key.channel();
			System.out.println("我要读了");
			ByteBuffer buffer = ByteBuffer.allocate(4);
			System.out.println("日");
			socketChannel.read(buffer);
			int size = buffer.getInt();
			System.out.println("看看大小:" + size);
			buffer = ByteBuffer.allocate(size);
			socketChannel.read(buffer);
			System.out.println("看客户都说了什么：" + new String(buffer.array()));
			key.interestOps(SelectionKey.OP_WRITE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void handleWrite(SelectionKey key) {

		try {
			System.out.println("我要写了");
			SocketChannel channel = (SocketChannel) key.channel();
			String welcome = "欢迎欢迎";
			byte[] welcomebytes = welcome.getBytes();
			int size = welcomebytes.length;
			ByteBuffer buffer = ByteBuffer.allocate(welcomebytes.length + 4);
			buffer.putInt(size);
			buffer.put(welcomebytes);
			channel.write(buffer);
			key.interestOps(SelectionKey.OP_READ);
			System.out.println("已向他表示欢迎，他是：" + count.get());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Server server = new Server();
		server.start();
	}
}
