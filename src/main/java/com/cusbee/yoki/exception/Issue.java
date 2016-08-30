package com.cusbee.yoki.exception;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * 
 * @author Dmytro Khodan
 * @date 28.06.2016
 * @project: yoki
 */
public class Issue implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer code;
	private String message;
	private IssueType issueType;
	private HttpStatus status;

	public Issue() {
		super();
	}

	public Issue(Integer code, String message, IssueType issueType) {
		super();
		this.code = code;
		this.message = message;
		this.issueType = issueType;
	}

	public Issue(Integer code, String message) {
		super();
		this.code = code;
		this.message = message;
		this.issueType = IssueType.ERROR;
	}

	public Issue(HttpStatus status, String message, IssueType issueType) {
		super();
		this.status = status;
		this.message = message;
		this.issueType = issueType;
	}

	public Issue(HttpStatus status, String message) {
		super();
		this.status = status;
		this.message = message;
		this.issueType = IssueType.ERROR;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public IssueType getIssueType() {
		return issueType;
	}

	public void setIssueType(IssueType issueType) {
		this.issueType = issueType;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}
}