package exceptions;

public class HeaderNotValidException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4652560568944982513L;

	public HeaderNotValidException() {
	}

	public HeaderNotValidException(String message) {
		super(message);
	}

	public HeaderNotValidException(Throwable cause) {
		super(cause);
	}

	public HeaderNotValidException(String message, Throwable cause) {
		super(message, cause);
	}

	public HeaderNotValidException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
