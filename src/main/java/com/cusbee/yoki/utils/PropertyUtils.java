package com.cusbee.yoki.utils;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.exception.BaseException;

/**
 * 
 * @author Dmytro Khodan
 * @date 28.06.2016
 * @project: yoki
 */

@Component(value = "PropertyUtils")
public class PropertyUtils {

	private static Properties properties;

	@PostConstruct
	public void init() throws IOException {
		Resource resource = new ClassPathResource("ErrorMessages.properties");
		properties = PropertiesLoaderUtils.loadProperties(resource);
	}
	
	public static String getProperty(int errorCode) {
		String returnValue = null;
		if (errorCode <= 0) {
			throw new ApplicationException("Invalid errorcode");
		}
		String errorCodeString = StringUtils.EMPTY + errorCode;
		if (properties != null) {
			returnValue = properties.getProperty(errorCodeString);
		}

		if (returnValue == null) {
			throw new ApplicationException(HttpStatus.BAD_REQUEST,
					errorCodeString);
		}
		return returnValue;
	}

	public static String getProperty(String propertyCode) {
		String returnValue = null;
		if (propertyCode == null) {
			throw new ApplicationException(
					ErrorCodes.Common.KEY_CANNOT_BE_EMPTY);
		}
		if (properties != null) {
			returnValue = properties.getProperty(propertyCode);
		}

		if (returnValue == null) {
			throw new ApplicationException(ErrorCodes.Common.NO_MAPPING_EXISTS,
					new String[] { propertyCode });
		}
		return returnValue;
	}

	public static String getProperty(int errorCode, String... dynamicValues)
			throws BaseException {

		String returnValue = PropertyUtils.getProperty(errorCode);

		return MessageFormat.format(returnValue, (Object[]) dynamicValues);

	}
}
