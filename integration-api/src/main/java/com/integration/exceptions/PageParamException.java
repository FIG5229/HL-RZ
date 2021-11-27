package com.integration.exceptions;

/**
 * page参数异常
 * 
 * @author dell
 *
 */
@SuppressWarnings("serial")
public class PageParamException extends RuntimeException{

	private String message;

	@Override
	public String getMessage() {
		return message;
	}

	public PageParamException(String message) {
		super();
		this.message = message;
	}
}
