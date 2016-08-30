package com.cusbee.yoki.controller;

import com.cusbee.yoki.dto.YokiResult;
import com.cusbee.yoki.exception.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    protected static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = {ApplicationException.class})
    public
    @ResponseBody
    YokiResult defaultErrorHandler(ApplicationException ae) {
        LOG.error("Application exception occurred: {}", ae.getStackTrace());
        return new YokiResult(ae.getStatus() != null ? ae.getStatus() : HttpStatus.INTERNAL_SERVER_ERROR,
                ae.getMessage(), ae.getMessage());
    }
}