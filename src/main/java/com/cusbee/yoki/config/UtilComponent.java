package com.cusbee.yoki.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * Created on 04.09.2016.
 */
@Component
public class UtilComponent {

    private static final String DEFAULT_TIMEZONE = "Europe/Kiev";


    private static Logger LOG = LoggerFactory.getLogger(UtilComponent.class);

    @PostConstruct
    public void setDefaultTimezone() {
        TimeZone.setDefault(TimeZone.getTimeZone(DEFAULT_TIMEZONE));
        LOG.debug("Default timezone was set to " + DEFAULT_TIMEZONE);
        System.out.println("Default timezone was set to " + DEFAULT_TIMEZONE);
    }
}
