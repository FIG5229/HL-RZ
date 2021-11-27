package com.integration.exceptions;

/**
 * 分页sql异常
 * 
 * @author dell
 *
 */
@SuppressWarnings("serial")
public class PageSqlException extends RuntimeException{
	private String message;

	@Override
	public String getMessage() {
		return message;
	}

	public PageSqlException(String message) {
		super();
		this.message = message;
	}
}
