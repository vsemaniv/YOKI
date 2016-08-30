package com.cusbee.yoki.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * 
 * @author Dmytro Khodan
 * @date 28.06.2016
 * @project: yoki
 */
public class YokiResult<T> extends ResponseEntity<T> {

	public static enum Status {
		SUCCESS, ERROR, WARNING, UNKNOWN
	}
	private String message;
	private T data;
	
	public YokiResult(){
		super(HttpStatus.OK);
	}
	
	public YokiResult(HttpStatus status, String message, T data){
		super(data, status);
		this.message = message;
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
}
