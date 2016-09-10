package com.cusbee.yoki.service.serviceimpl;

import com.cusbee.yoki.entity.CourierDetails;
import com.cusbee.yoki.entity.Order;
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
    private static final int retries = 10;

    private static final String DELIVERY_TITLE = "Час забирати замовлення!";
    private static final String REJECTED_TITLE = "Скасовано замовлення!";

    @Override
    public void summonCourier(CourierDetails courier) {
        String token = getCourierToken(courier);
        sendPushNotification(token, DELIVERY_TITLE, "Вам потрібно дістатися бази до " + courier.getTimeToArrive());
    }

    @Override
    public void cancelSummonCourier(CourierDetails courier) {
        String token = getCourierToken(courier);
        sendPushNotification(token, "Призначення скасовано", "На базу наразі йти не потрібно.");
    }

    @Override
    public void notifyAboutDeclinedOrder(CourierDetails courier, Order order) {
        final String message = String.format("Замовлення номер %1d скасовано.", order.getId());
        String token = getCourierToken(courier);
        sendPushNotification(token, REJECTED_TITLE, message);
    }


    public void sendPushNotification(final String token, final String title, final String message) {
        Thread courierNotifier = new Thread(new Runnable() {
            @Override
            public void run() {
                Sender sender = new Sender(API_KEY);
                Message msg = new Message.Builder()
                        .addData("title", title)
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
        });
        courierNotifier.start();
    }

    private String getCourierToken(CourierDetails courier) {
        String token = courier.getMessagingToken();
        if (StringUtils.isEmpty(token)) {
            throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, "Courier device is not registered for notifications!");
        }
        return token;
    }
    /*
    public static void main(String[] args) {
        String token = "e4rxhodtM2c:APA91bH2a1SfLAvzcjw1f2SvuvUchRnsnYixOaLdTcVXe1mJW5DuFk2O_t0gb2RiM6ANyxF7PSKsKUo4pI0vlO_Zo5zCZb5Pm6WYorsubhMM7H8uK1s748FEE7XmTBiZWoprCSl-wFXQ";

        new MessagingServiceImpl().sendPushNotification(token,"Оп-оп",3L, false);
    }*/
}
