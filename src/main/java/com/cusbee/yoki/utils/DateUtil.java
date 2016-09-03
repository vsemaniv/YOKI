package com.cusbee.yoki.utils;

import com.cusbee.yoki.exception.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created on 01.09.2016.
 */
public class DateUtil {
    private static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static Logger LOG = LoggerFactory.getLogger(DateUtil.class);

    static {
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Kiev"));
        System.out.println("Default timezone was set to Europe/Kiev.");
        LOG.debug("Default timezone was set to Europe/Kiev.");
    }

    public static Date getDate(String dateTime) {
        Calendar cal = Calendar.getInstance();
        try {
            return SDF.parse(dateTime);
        } catch (ParseException e) {
            throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to parse date", e);
        }
    }

    public static Calendar getCalendar(String dateTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDate(dateTime));
        return calendar;
    }
}
