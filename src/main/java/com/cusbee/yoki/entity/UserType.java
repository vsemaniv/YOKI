package com.cusbee.yoki.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Dmytro Khodan
 * @date 28.06.2016
 * @project: yoki
 */
public enum UserType {

	ADMINISTRATOR("Administrator"), OPERATOR("Operator"), 
	KITCHEN("Kitchen"), DRIVER("Driver"), USER("User");
	
	private String value;

	static Map<String, UserType> map = new HashMap<String, UserType>();

	static {
		UserType[] status = UserType.values();
		for (int i = 0; i < status.length; i++) {
			UserType s = status[i];
			map.put(s.getValue().toUpperCase(), s);
			map.put(s.name(), s);
		}

	}

	private UserType(String value) {
		this.value = value;
	}

	public static UserType fromValue(String v) {
		return map.get(v.toUpperCase());
	}

	public String getValue() {
		return value;
	}

	public static boolean isValidAccountType(String expectedValue,
			UserType actualValue) {
		UserType exp = map.get(expectedValue.toUpperCase());
		return exp == actualValue;
	}
}
