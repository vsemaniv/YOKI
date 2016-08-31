package com.cusbee.yoki.service.serviceimpl;

import com.cusbee.yoki.service.MessagingService;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MessagingServiceImpl implements MessagingService {

    private static final Logger LOG = LoggerFactory.getLogger(MessagingServiceImpl.class);

    private static final String API_KEY = "AIzaSyBR_JUYj5sNtNxwQUv9lV4OcDSqB55qi_c";
    //number of attempts to resend notification if previous attempt failed
    private static final int retries = 5;


    public boolean sendPushNotification(String token, String message) {
        Sender sender = new Sender(API_KEY);
        Message msg = new Message.Builder()
                .addData("title", "Час забирати замовлення!")
                .addData("message", message)
                .build();
        Result result;
        try {
            result = sender.send(msg, token, retries);
            if (StringUtils.isEmpty(result.getErrorCodeName())) {
                LOG.debug("GCM Notification was sent successfully: {}", result);
                return true;
            } else {
                LOG.error("Error while sending push notification: {}", result.getErrorCodeName());
            }
        } catch (Exception e) {
            LOG.error("Exception occurred : {}", e.getStackTrace());
        }
        return false;
    }
}
