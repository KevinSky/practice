package kevin.socket.chat.common.communication;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

import kevin.socket.chat.common.ServerConstans;
import kevin.socket.chat.common.exception.BadCommandException;

public class CommunicationUtil {

//	public static void sendCommand(Socket socket, int command) throws IOException {
//		sendMsg(socket, command+"");
//	}
//	public static int receiveCommand(Socket socket) throws IOException, BadCommandException {
//		String command = receiveMsg(socket);
//		if(command!=null && command.length()>0) {
//			try {
//				int c = Integer.parseInt(command);
//			} catch (NumberFormatException e) {
//				throw new BadCommandException("解析命令出错",e);
//			}
//		}
//	}
	
	public static void receiveFile(Socket socket, String path)
			throws IOException {
		OutputStream out = new FileOutputStream(path);
		InputStream input = socket.getInputStream();

		int size = 512;
		byte[] buf = new byte[size];
		int k = 1;
		while (k > 0) {
			k = input.read(buf);
			out.write(buf, 0, k);
			if (k < size)
				break;
		}
		out.close();
	}

	public static void sendFile(Socket socket, String path) throws IOException {
		InputStream input = new FileInputStream(path);
		OutputStream out = socket.getOutputStream();

		int size = 512;
		byte[] buf = new byte[size];
		int k = 1;
		while (k > 0) {
			k = input.read(buf);
			out.write(buf, 0, k);
			if (k < size)
				break;
		}
		input.close();
	}

	public static void sendMsg(Socket socket, String msg) throws IOException {
		OutputStream out = socket.getOutputStream();
		byte[] msgB = msg.getBytes();
		int size = msgB.length;
		ByteBuffer buffer = ByteBuffer.allocate(size+4);
		buffer.putInt(size);
		buffer.put(msgB, 0, msgB.length);
		out.write(buffer.array());
	}

	public static String receiveMsg(Socket socket)
			throws IOException {
		InputStream input = socket.getInputStream();
		byte[] buf = new byte[4];
		input.read(buf);
		ByteBuffer buffer = ByteBuffer.allocate(4);
		buffer.put(buf);
		int size = buffer.getInt(0);
		System.out.println("看看大小："+size);
		buf = new byte[size];
		input.read(buf);
		return new String(buf,
				ServerConstans.MESSAGE_ENCODE);
		
	}
}
