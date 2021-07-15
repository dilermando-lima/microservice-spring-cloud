package com.formcloud.archcommons.errorhandler;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException  {

	private int status;
	
	private String message;

	public ApiException(HttpStatus httpStatus , String message ) {
		this.message = message;
		this.status = httpStatus.value();
	}

	public int getStatus() {
		return status;
	}


	public String getMessage() {
		return message;
	}

}
