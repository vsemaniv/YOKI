package com.cusbee.yoki.utils;

import com.cusbee.yoki.exception.ApplicationException;
import org.springframework.http.HttpStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created on 01.09.2016.
 */
public class DateUtil {
    private static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
