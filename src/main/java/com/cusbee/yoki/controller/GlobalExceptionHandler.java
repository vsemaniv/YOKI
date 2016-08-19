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

    @ExceptionHandler(value = {ApplicationException.class, Throwable.class})
    public
    @ResponseBody
    YokiResult defaultErrorHandler(Throwable throwable) {
        LOG.error(throwable.getMessage(), throwable);
        ApplicationException ae;
        if (throwable instanceof ApplicationException) {
            ae = (ApplicationException) throwable;
            return new YokiResult(YokiResult.Status.ERROR, ae.getMessage(), ae.getErrorCode());
        } else if(throwable instanceof NullPointerException) {
            return new YokiResult(YokiResult.Status.ERROR, "NullPointerException", throwable.getStackTrace());
        } else {
            Throwable cause = throwable;
            while (cause.getCause() != null) {
                cause = cause.getCause();
            }
            return new YokiResult(YokiResult.Status.ERROR, throwable.getMessage(), "Main cause: " + cause.getMessage());
        }
    }
}