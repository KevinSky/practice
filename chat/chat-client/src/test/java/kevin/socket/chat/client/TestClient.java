package kevin.socket.chat.client;

import kevin.socket.chat.common.ServerConstans;

public class TestClient {

	public static void testPerformance() {
		int size = ServerConstans.TEST_SIZE + 1;
		for(int i=0; i<size; i++) {
			Client client = new Client();
			client.start();
		}
	}
	
	public static void main(String[] args) {
		testPerformance();
	}
}
