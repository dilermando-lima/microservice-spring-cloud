package com.formcloud.springutil.errorhandler;

public class ErrorReturn {

	private final int status;
	private final String message;
	
	public ErrorReturn(String message, int status) {
		this.message = message;
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

}
