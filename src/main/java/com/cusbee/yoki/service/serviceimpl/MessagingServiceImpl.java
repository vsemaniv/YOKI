package com.cusbee.yoki.service.serviceimpl;

import com.cusbee.yoki.entity.CourierDetails;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.service.MessagingService;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MessagingServiceImpl implements MessagingService {

    private static final Logger LOG = LoggerFactory.getLogger(MessagingServiceImpl.class);

    private static final String API_KEY = "AIzaSyBR_JUYj5sNtNxwQUv9lV4OcDSqB55qi_c";
    //number of attempts to resend notification if previous attempt failed
    private static final int retries = 5;

    private static final String TITLE = "Час забирати замовлення!";
    private static final String MESSAGE_PATTERN = "Прибуття на базу: %1$tR.\r\n Доставити до: %2$tR.";

    @Override
    public void notifyCourier(final CourierDetails courier, final Date timeToTake, final Date timeToDeliver) {
        Thread courierNotifier = new Thread(new Runnable() {
            @Override
            public void run() {
                String token = courier.getMessagingToken();
                if(StringUtils.isEmpty(token)) {
                    throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, "Courier device is not registered for notifications!");
                }
                String message = String.format(MESSAGE_PATTERN, timeToTake, timeToDeliver);
                sendPushNotification(token, message);
            }
        });
        courierNotifier.start();

    }

    public void sendPushNotification(String token, String message) {
        Sender sender = new Sender(API_KEY);
        Message msg = new Message.Builder()
                .addData("title", TITLE)
                .addData("message", message)
                .build();
        Result result;
        try {
            result = sender.send(msg, token, retries);
            if (StringUtils.isEmpty(result.getErrorCodeName())) {
                LOG.debug("GCM Notification was sent successfully: {}", result);
            } else {
                LOG.error("Error while sending push notification: {}", result.getErrorCodeName());
            }
        } catch (Exception e) {
            LOG.error("Exception occurred : {}", e.getStackTrace());
        }
    }
}
