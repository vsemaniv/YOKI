package com.cusbee.yoki.service;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.utils.ErrorCodes;

@Service(value="NullPointerServiceImpl")
public class NullPointerServiceImpl {
	
	
	/**
	 * Method checks if request parameters is not null
	 * @param object
	 * @throws BaseException
	 */
	public void isNull(Object object) throws BaseException{
		if(Objects.isNull(object)){
			throw new ApplicationException(ErrorCodes.Common.EMPTY_REQUEST, "Bad Request. NullPointerException");
		}
	}
	
}
