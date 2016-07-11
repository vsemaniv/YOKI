package com.cusbee.yoki.utils;

/**
 * 
 * @author Dmytro Khodan
 * @date 28.06.2016
 * @project: yoki
 */
public class ErrorCodes {

	public interface Dish {
		int EMPTY_REQUEST = 0000;
		int EMPTY_FIELD = 0001;
		int ALREADY_EXISTS = 0002;
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
	
	public interface Category {
		
		int EMPTY_REQUEST = 3000;
		int EMPTY_FIELD = 3001;
		int ALREADY_EXIST = 3002;
	}
	
	public interface Ingredient {
		int EMPTY_REQUEST = 4000;
		int EMPTY_FIELD = 4001;
		int ALREADY_EXIST = 4002;
	}

	public interface  Operator {
		int EMPTY_REQUEST = 5000;
	}
}
