package com.cusbee.yoki.service;

public interface MessagingService {
    boolean sendPushNotification(String token, String message);
}
