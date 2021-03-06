package com.cusbee.yoki.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Dmytro Khodan
 * @date 28.06.2016
 * @project: yoki
 */
public enum AuthorityName {

	ADMINISTRATOR("Administrator"), OPERATOR("Operator"), 
	KITCHEN("Kitchen"), DRIVER("Driver"), USER("User");
	
	private String value;

	static Map<String, AuthorityName> map = new HashMap<String, AuthorityName>();

	static {
		AuthorityName[] status = AuthorityName.values();
		for (int i = 0; i < status.length; i++) {
			AuthorityName s = status[i];
			map.put(s.getValue().toUpperCase(), s);
			map.put(s.name(), s);
		}

	}

	private AuthorityName(String value) {
		this.value = value;
	}

	public static AuthorityName fromValue(String v) {
		return map.get(v.toUpperCase());
	}

	public String getValue() {
		return value;
	}

	public static boolean isValidAccountType(String expectedValue,
			AuthorityName actualValue) {
		AuthorityName exp = map.get(expectedValue.toUpperCase());
		return exp == actualValue;
	}
}
