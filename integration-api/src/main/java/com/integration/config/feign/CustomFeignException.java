package com.integration.config.feign;

/**
 * feign调用出错抛出此类异常，用做区分feign调用与其他调用
 */
public class CustomFeignException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Integer code;
	private String exceptionMsg;

	public CustomFeignException(String msg) {
		super(msg);
		this.code = 500;
	}

	public CustomFeignException(String msg, Integer code) {
		super(msg);
		this.code = code;
	}

	public CustomFeignException(String msg, String exceptionMsg) {
		super(msg);
		this.code = 500;
		this.setExceptionMsg(exceptionMsg);
	}

	public CustomFeignException(String msg, Integer code, String exceptionMsg) {
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