package kevin.socket.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kevin.socket.chat.common.ServerConstans;
import kevin.socket.chat.server.client.ClientSocketThread;

import org.apache.log4j.Logger;

public class ChatServer {
	private ServerSocket serverSocket;
	private static Logger log = Logger.getLogger(ChatServer.class);

	private ExecutorService executor;
	private int poolSize = 10;

	public void start() throws IOException {
		serverSocket = new ServerSocket(ServerConstans.SERVER_PORT);
		executor = Executors.newFixedThreadPool(poolSize);
		int size = 0;
		long start = 0;
		while (true) {
			size++;
			log.info("等待客户端连接，这将是第"+size+"个用户");
			Socket socket = serverSocket.accept();
			if(start==0)
				start = System.currentTimeMillis();
			log.info("来了一个新任务，开一个线程，这是第" + size + "个线程");
			executor.submit(new ClientSocketThread(socket));
			log.info("good，提交了");
			if(size==(ServerConstans.TEST_SIZE)) {
				long end = System.currentTimeMillis();
				log.info( ServerConstans.TEST_SIZE + "\t" + (end - start) );
			}
				
		}
	}
}
