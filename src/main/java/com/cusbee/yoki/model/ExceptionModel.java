package com.cusbee.yoki.model;

/**
 * Created on 05.09.2016.
 */
public class ExceptionModel {
    private String message;

    private StackTraceElement[] elements;

    public ExceptionModel(Exception e) {
        Throwable first = e;
        while(first.getCause() != null && first.getCause() != first) {
            first = first.getCause();
        }
        this.message = first.getMessage();
        this.elements = first.getStackTrace();

    }

    public ExceptionModel() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public StackTraceElement[] getElements() {
        return elements;
    }

    public void setElements(StackTraceElement[] elements) {
        this.elements = elements;
    }
}
