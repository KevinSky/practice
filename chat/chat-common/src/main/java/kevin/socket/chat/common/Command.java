package kevin.socket.chat.common;

import java.util.HashMap;
import java.util.Map;

public class Command {

	public static int SEND_MESSAGE = 1;
	public static int CLIENT_ASK_WELCOME = 2;
	/** 服务器发送文件给客户端	*/
	public static int SERVER_SEND_FIIE = 3;
	
	public static Map<Integer,String> descriptions = new HashMap<Integer, String>();
	
	static {
		descriptions.put(SEND_MESSAGE, "发送短文本消息");
		descriptions.put(CLIENT_ASK_WELCOME, "客户端请求欢迎消息");
		descriptions.put(SERVER_SEND_FIIE, "客户端请求欢迎消息");
	}
	
}
