package kevin.socket.chat.common.atom;

public class AtomUtil {

	private static int inc = 0;
	
	public static synchronized int getNextId() {
		return ++inc;
	}
}
