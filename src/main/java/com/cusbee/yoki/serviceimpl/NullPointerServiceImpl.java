package com.cusbee.yoki.serviceimpl;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.service.NullPointerService;
import com.cusbee.yoki.utils.ErrorCodes;

@Service
public class NullPointerServiceImpl implements NullPointerService{
	
	
	/**
	 * Method checks if request parameters is not null
	 * @param object
	 * @throws BaseException
	 */
	@Override
	public void isNull(Object object) throws BaseException{
		if(Objects.isNull(object)){
			throw new ApplicationException(ErrorCodes.Common.EMPTY_REQUEST, "Bad Request. NullPointerException");
		}
	}
	
}
