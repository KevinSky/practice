package kevin.practice.nio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;

public class Client {
	Socket socket;
	SocketChannel socketChannel;
	Selector selector;
	

	public void start() {
		try {
			Socket socket = new Socket("127.0.0.1",8889);
			InputStream input = socket.getInputStream();
			byte[] b = new byte[4];
			input.read(b);
			int size = b[0]>>3+b[2]>>2+b[1]>>1+b[0];
			System.out.println("看看大小:"+size);
			b = new byte[size];
			System.out.println("看服务对我多好："+new String(b));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void start2() {
		try {
			selector = SelectorProvider.provider().openSelector();
			InetSocketAddress address = new InetSocketAddress("127.0.0.1", 8889);
//			socketChannel.connect(address);
			socketChannel = SocketChannel.open(address);
			socketChannel.configureBlocking(false);
			socketChannel.register(selector, SelectionKey.OP_READ);
			socketChannel.register(selector, SelectionKey.OP_WRITE);
			
			System.out.println("连上了："+socketChannel.isConnected());
			selector.select();
			System.out.println("有变动");
			Iterator<SelectionKey> selectedKeys = selector.selectedKeys()
					.iterator();
			while (selectedKeys.hasNext()) {
				SelectionKey key = selectedKeys.next();
				selectedKeys.remove();
				
				if(key.isReadable())
					handleRead(key);
				else if(key.isWritable()) {
					handleWrite(key);
				} else {
					System.out.println("这是什么状态啊");
				}
			}
			
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
			System.out.println("看服务器都说了什么：" + new String(buffer.array()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void handleWrite(SelectionKey key) {

		try {
			System.out.println("我要写了");
			SocketChannel channel = (SocketChannel) key.channel();
			String welcome = "我是客户啊";
			byte[] welcomebytes = welcome.getBytes();
			int size = welcomebytes.length;
			ByteBuffer buffer = ByteBuffer.allocate(welcomebytes.length + 4);
			buffer.putInt(size);
			buffer.put(welcomebytes);
			channel.write(buffer);
			System.out.println("已向服务器表示hello");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Client client = new Client();
		client.start();
	}
}
