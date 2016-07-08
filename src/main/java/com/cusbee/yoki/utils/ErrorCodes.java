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
	
	public interface User {
		
		int EMPTY_REQUEST = 2000;
		int EMPTY_FIELDS = 2001;
		int ALREADY_EXIST = 2002;
		int BAD_REQUEST = 2003;
		int USER_UNVAILABLE = 2004;
		
	}
	
}
