package com.cusbee.yoki.utils;

import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.exception.BaseException;

public class YokiExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if(e instanceof BaseException) {
            ApplicationException ae = (ApplicationException) e;
        }
    }
}
