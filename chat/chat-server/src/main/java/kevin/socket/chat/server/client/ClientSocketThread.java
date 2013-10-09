package kevin.socket.chat.server.client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import kevin.socket.chat.common.atom.AtomUtil;
import kevin.socket.chat.common.communication.CommunicationUtil;

import org.apache.log4j.Logger;

public class ClientSocketThread implements Runnable{
	private Socket socket = null;
	private OutputStream out = null;
	private static Logger log = Logger.getLogger(ClientSocketThread.class);

	public ClientSocketThread(Socket socket) {
		this.socket = socket;
	}
	@Override
	public void run() {
		log.info("good,我要开工了");
		try {
			out = socket.getOutputStream();
		} catch (IOException e) {
			log.error("获取客户端输出流失败！",e);
			return;
		}
		this.welcome();
		this.theBack();
		this.sendImage();
		try {
			this.socket.close();
		} catch (IOException e) {
			log.error("关闭socket出错",e);
		}
		log.info("结束了");
	}
	
	private void welcome() {
		try {
			String welcome = "您好，欢迎来到chat世界，在这里，你将交到很多MM！";
			log.info("准备发消息 ");
			CommunicationUtil.sendMsg(socket, welcome);
			log.info("欢迎消息发送完毕!");
		} catch (IOException e) {
			log.error("表示欢迎失败",e);
		}
	}
	
	private void theBack() {
		try {
			String welcome = CommunicationUtil.receiveMsg(socket);
			log.info("看看客户说了什么："+welcome);
		} catch (IOException e) {
			log.error("取服务器的欢迎消息失败！", e);
		}
	}
	
	public void sendImage(){
		String path = "F:/";
		String filename = "宏碁笔记本保修";
		int count = AtomUtil.getNextId();
		try {
			// 先发文件名
			CommunicationUtil.sendMsg(socket, filename + count + ".jpg");
//			String ok = CommunicationUtil.receiveMsg(socket);
			CommunicationUtil.sendFile(socket, path + filename + ".jpg");
		} catch (IOException e) {
			log.error("发送文件失败,path:"+path,e);
		}
	}

}
