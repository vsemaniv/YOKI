package com.cusbee.yoki.logging;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

/**
 * 
 * @author Dmytro Khodan
 * @date 28.06.2016
 * @project: yoki
 */
public class LogFactory extends Logger {
	
	protected LogFactory(String name) {
		super(name);
	}

	public static Logger getLog(Class<?> value) {
		return Logger.getLogger(value);

	}

	public static Logger getLog(String value) {
		return Logger.getLogger(value);
	}

	public static Logger getLog(String name, LoggerFactory factory) {
		return Logger.getLogger(name, factory);
	}

}
