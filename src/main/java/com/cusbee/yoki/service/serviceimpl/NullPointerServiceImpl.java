package com.cusbee.yoki.service.serviceimpl;

import java.util.Objects;

import org.springframework.http.HttpStatus;
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
	public void isNull(Object object){
		if(Objects.isNull(object)){
			throw new ApplicationException(HttpStatus.BAD_REQUEST, "Bad Request. Null id was sent. NullPointerException");
		}
	}

}
