package kevin.socket.chat.common.exception;

public class BadCommandException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8770269501485975274L;

	public BadCommandException() {
	}
	
	public BadCommandException(String msg) {
		super(msg);
	}
	
	public BadCommandException(String msg, Exception e) {
		super(msg,e);
	}
}
