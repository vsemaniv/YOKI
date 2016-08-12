package com.cusbee.yoki.dto;

/**
 * 
 * @author Dmytro Khodan
 * @date 28.06.2016
 * @project: yoki
 */
public class YokiResult<T> {

	public static enum Status {
		SUCCESS, ERROR, WARNING, UNKNOWN
	}
	
	private Status status;
	private String message;
	private T data;
	
	public YokiResult(){
	}
	
	public YokiResult(Status status, String message, T data){
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
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
