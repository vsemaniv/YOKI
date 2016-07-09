package com.cusbee.yoki.security;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
/**
 * 
 * @author Dmytro Khodan
 * @date 09.07.2016
 * @project: yoki
 */
public class ModelBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
