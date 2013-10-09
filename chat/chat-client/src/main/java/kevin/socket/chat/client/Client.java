package kevin.socket.chat.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;

import kevin.socket.chat.common.Command;
import kevin.socket.chat.common.ServerConstans;
import kevin.socket.chat.common.communication.CommunicationUtil;

import org.apache.log4j.Logger;

public class Client extends Thread{
	private static Logger log = Logger.getLogger(Client.class);
	private Socket socket = null;
	private InputStream input = null;
	private static int TIMEOUT = 5000;

	public void run() {
		try {
			this.connect();
		} catch (IOException e) {
			log.error("连接服务器失败，退出!", e);
			return;
		}

		try {
			input = socket.getInputStream();
		} catch (IOException e) {
			log.error("获取服务器输出流失败，退出!", e);
			return;
		}
		showWelcome();
		showBackWelcome();
		acceptImage();
		try {
			socket.close();
		} catch (IOException e) {
			log.error("关闭socket出错",e);
		}
	}

	public void connect() throws IOException {
		socket = new Socket();
		SocketAddress address = new InetSocketAddress(ServerConstans.SERVER_IP,
				ServerConstans.SERVER_PORT);
		socket.connect(address,TIMEOUT);
	}

	public void showWelcome() {
		try {
			String welcome = CommunicationUtil.receiveMsg(socket);
			log.info("服务器很欢迎我呀，内容为："+welcome);
		} catch (IOException e) {
			log.error("取服务器的欢迎消息失败！", e);
		}
	}
	
	public void showBackWelcome(){
		try {
			CommunicationUtil.sendMsg(socket, "我也很开心，哈哈！！！");
		} catch(Exception e){
			log.error("回敬失败",e);
		}
	}
	
	public void acceptImage(){
		String path = "d:/test/";
		try {
			String filename = CommunicationUtil.receiveMsg(socket);
			log.info("收到的文件名为："+filename);
//			CommunicationUtil.sendMsg(socket, "OK");
			CommunicationUtil.receiveFile(socket,path+filename);
		} catch (IOException e) {
			log.error("保存文件失败,path:"+path,e);
		}
	}

	public static void main(String[] args) {
		Client client = new Client();
		client.start();
	}
}
