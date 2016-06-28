package com.cusbee.yoki.exception;

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

}