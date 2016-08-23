package com.cusbee.yoki.controller;

import com.cusbee.yoki.dto.YokiResult;
import com.cusbee.yoki.exception.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        LOG.error(ae.getMessage(), ae);
        return new YokiResult(YokiResult.Status.ERROR, ae.getMessage(), ae.getErrorCode());
    }
}