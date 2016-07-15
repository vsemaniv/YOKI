package com.cusbee.yoki.exception;

import com.cusbee.yoki.utils.PropertyUtils;

/**
 * 
 * @author Dmytro Khodan
 * @date 28.06.2016
 * @project: yoki
 */
public class ApplicationException extends BaseException {

	private static final long serialVersionUID = 1L;

	public ApplicationException(final String msg) {
		super(msg);
	}

	public ApplicationException(final String msg, Throwable e) {
		super(msg, e);
	}

	public ApplicationException(int code, String msg, Issues e) {
		super(code, msg, e);
	}

	public ApplicationException(int code, String msg, Throwable e) {
		super(code, msg, e);
	}

	public ApplicationException(int code, String msg) {
		super(code, msg);
	}

	public ApplicationException(int code) throws BaseException {
		super(code, PropertyUtils.getProperty(code));
	}

	public ApplicationException(int code, String[] dynamicErrorMessage)
			throws BaseException {
		super(code, PropertyUtils.getProperty(code, dynamicErrorMessage));
	}

	public ApplicationException(int code, String[] dynamicErrorMessage,
			IssueType issueType) throws BaseException {
		super(code, PropertyUtils.getProperty(code, dynamicErrorMessage),
				issueType);
	}

	public ApplicationException(int code, Throwable e,
			String[] dynamicErrorMessage) throws BaseException {
		super(code, PropertyUtils.getProperty(code, dynamicErrorMessage), e);
	}

	public ApplicationException(int code, String msg, IssueType issueType) {
		super(code, msg, issueType);
	}
	
}