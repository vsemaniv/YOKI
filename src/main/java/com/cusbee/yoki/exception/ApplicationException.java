package com.cusbee.yoki.exception;

import java.io.Serializable;

import com.cusbee.yoki.utils.PropertyUtils;

/**
 * 
 * @author Dmytro Khodan
 * @date 28.06.2016
 * @project: yoki
 */
public class ApplicationException extends BaseException implements Serializable {

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

<<<<<<< HEAD
	public ApplicationException(int code) {
=======
	public ApplicationException(int code) throws BaseException {
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
		super(code, PropertyUtils.getProperty(code));
	}

	public ApplicationException(int code, String[] dynamicErrorMessage)
			throws BaseException {
		super(code, PropertyUtils.getProperty(code, dynamicErrorMessage));
	}

	public ApplicationException(int code, String[] dynamicErrorMessage,
<<<<<<< HEAD
			IssueType issueType) {
=======
			IssueType issueType) throws BaseException {
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
		super(code, PropertyUtils.getProperty(code, dynamicErrorMessage),
				issueType);
	}

	public ApplicationException(int code, Throwable e,
<<<<<<< HEAD
			String[] dynamicErrorMessage) {
=======
			String[] dynamicErrorMessage) throws BaseException {
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
		super(code, PropertyUtils.getProperty(code, dynamicErrorMessage), e);
	}

	public ApplicationException(int code, String msg, IssueType issueType) {
		super(code, msg, issueType);
	}
	
}
