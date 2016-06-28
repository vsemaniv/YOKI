package com.cusbee.yoki.utils;

/**
 * 
 * @author Dmytro Khodan
 * @date 28.06.2016
 * @project: yoki
 */
public class ErrorCodes {

	public interface Dish {
		
	}
	
	public interface Common {

		int KEY_CANNOT_BE_EMPTY = 1000;
		int NO_MAPPING_EXISTS = 1001;
		int INVALID_DATE_FORMAT = 1002;
		int EMPTY_REQUEST = 1003;
		int INVALID_REQUEST = 1004;
		int INVALID_PROFILE = 1005;
	}
	
}
