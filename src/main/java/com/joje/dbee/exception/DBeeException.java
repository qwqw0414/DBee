package com.joje.dbee.exception;

import com.joje.dbee.common.contents.StatusCode;

public class DBeeException extends RuntimeException {

	private static final long serialVersionUID = 4868026318000140605L;

	private StatusCode status;
	
	public DBeeException() {
		super();
	}
	
	public DBeeException(StatusCode status) {
		super(status.getMessage());
		this.status = status;
	}

	public DBeeException(String message) {
		super(message);
	}
	
	public DBeeException(Throwable cause) {
		super(cause);
	}
	
	public DBeeException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public StatusCode getStatus() {
		return status;
	}
}
