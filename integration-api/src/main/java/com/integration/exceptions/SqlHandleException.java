package com.integration.exceptions;

/**
 * sqlhandle异常
 * 
 * @author dell
 *
 */
@SuppressWarnings("serial")
public class SqlHandleException extends RuntimeException {

	private String message;

	@Override
	public String getMessage() {
		return message;
	}

	public SqlHandleException(String message) {
		super();
		this.message = message;
	}
}
