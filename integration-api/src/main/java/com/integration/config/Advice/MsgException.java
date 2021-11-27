package com.integration.config.Advice;

public class MsgException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer code;
	private String exceptionMsg;

	public MsgException(String msg) {
		super(msg);
		this.code = 500;
	}
	
	public MsgException(String msg, Integer code) {
		super(msg);
		this.code = code;
	}
	
	public MsgException(String msg,String exceptionMsg) {
		super(msg);
		this.code = 500;
		this.setExceptionMsg(exceptionMsg);
	}

	public MsgException(String msg, Integer code,String exceptionMsg) {
		super(msg);
		this.code = code;
		this.setExceptionMsg(exceptionMsg);
		
	}
	
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getExceptionMsg() {
		return exceptionMsg;
	}

	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}
}