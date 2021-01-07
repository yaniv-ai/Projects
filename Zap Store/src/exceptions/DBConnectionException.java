package exceptions;

import java.sql.SQLException;

public class DBConnectionException extends SQLException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DBConnectionException() {
		super();
	}

	public DBConnectionException(String reason, String sqlState,
			int vendorCode, Throwable cause) {
		super(reason, sqlState, vendorCode, cause);
	}

	public DBConnectionException(String reason, String SQLState, int vendorCode) {
		super(reason, SQLState, vendorCode);
	}

	public DBConnectionException(String reason, String sqlState, Throwable cause) {
		super(reason, sqlState, cause);
	}

	public DBConnectionException(String reason, String SQLState) {
		super(reason, SQLState);
	}

	public DBConnectionException(String reason, Throwable cause) {
		super(reason, cause);
	}

	public DBConnectionException(String reason) {
		super(reason);
	}

	public DBConnectionException(Throwable cause) {
		super(cause);
	}

}
