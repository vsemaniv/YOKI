package com.cusbee.yoki.model.notifications;

/**
 * Created on 30.08.2016.
 */
public class PushNotificationModel {
    String token;
    String message;

    public PushNotificationModel() {
    }

    public PushNotificationModel(String token, String message) {
        this.token = token;
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
