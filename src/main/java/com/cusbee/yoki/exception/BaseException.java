package com.cusbee.yoki.exception;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Dmytro Khodan
 * @date 28.06.2016
 * @project: yoki
 */
public class BaseException extends RuntimeException implements Serializable {

	private static final Logger LOG = LoggerFactory.getLogger(BaseException.class);

	private static final long serialVersionUID = 1L;

	private Issues issues = new Issues();

	private int errorCode;

	private String message;

	public BaseException(final String message) {

		LOG.error("ErrorMessage: {}", message);
		this.message = message;
	}

	public BaseException(final Throwable e) {
		super(e.getMessage(), e);
	}

	public BaseException(int code, String message) {
		this.errorCode = code;
		this.message = message;
		LOG.error("ErrorCode: {}, Error Message: {}", code, message);
		issues.add(new Issue(code, message));
	}

	public BaseException(int code, String message, IssueType issueType) {
		this.errorCode = code;
		this.message = message;
		LOG.error("ErrorCode: {}, Error Message: {},  IssueType: {}", code, message, issueType);
		issues.add(new Issue(code, message, issueType));
	}

	public BaseException(final String msg, final Throwable exception) {
		super(msg, exception);
		this.message = msg;
	}

	public BaseException(final int errorCode, final String message,
			final Throwable exception) {
		super(message, exception);
		this.errorCode = errorCode;
		this.message = message;
		LOG.error("ErrorCode: {}, Error Message: {}", errorCode, message);
	}

	public BaseException(int errorCode, String message, Issues issue) {
		this.errorCode = errorCode;
		this.message = message;
		this.issues = issue;
		LOG.error("ErrorCode: {}, Error Message: {}", errorCode, message);
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public List<Issue> getIssues() {
		return issues.getIssues();
	}

	public void setIssues(Issues issues) {
		this.issues = issues;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean anyErrorInIssue() {

		List<Issue> issues = this.getIssues();

		if (CollectionUtils.isNotEmpty(issues)) {
			for (Issue issue : issues) {
				if (IssueType.ERROR == issue.getIssueType()) {
					return true;
				}
			}
		}
		return false;
	}
}
