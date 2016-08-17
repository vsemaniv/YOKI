package com.cusbee.yoki.controller;

import com.cusbee.yoki.dto.YokiResult;
import com.cusbee.yoki.exception.ApplicationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(value = {ApplicationException.class, Exception.class})
    public @ResponseBody YokiResult defaultErrorHandler(Exception e) {
        ApplicationException ae;
        if(e instanceof ApplicationException) {
            ae = (ApplicationException) e;
            return new YokiResult(YokiResult.Status.ERROR, ae.getMessage(), ae.getErrorCode());
        } else {
            return new YokiResult(YokiResult.Status.ERROR, e.getMessage(), e);
        }
    }
}