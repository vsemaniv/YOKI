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
    private static final String REJECTED_TITLE = "Скасоване замовлення!";
    private static final String MESSAGE_PATTERN = "Прибуття на базу: %1$tR.\r\n Доставити до: %2$tR.";

    @Override
    public void notifyCourier(final CourierDetails courier, final Order order) {
        Thread courierNotifier = new Thread(new Runnable() {
            @Override
            public void run() {
                Date timeToTake = order.getTimeToTake().getTime();
                Date timeToDeliver = order.getTimeToDeliver().getTime();
                String token = getCourierToken(courier);
                String message = String.format(MESSAGE_PATTERN, timeToTake, timeToDeliver);
                sendPushNotification(token, message, order.getId(), false);
            }
        });
        courierNotifier.start();
    }

    public void releaseCourier(final CourierDetails courier, final Order order) {
        final String message = String.format("Замовлення номер %1d скасовано.", order.getId());
        Thread courierNotifier = new Thread(new Runnable() {
            @Override
            public void run() {
                String token = getCourierToken(courier);
                sendPushNotification(token, message, order.getId(), true);
            }
        });
        courierNotifier.start();
    }


    public void sendPushNotification(String token, String message, Long orderId, boolean declined) {
        Sender sender = new Sender(API_KEY);
        Message msg = new Message.Builder()
                .addData("title", declined ? REJECTED_TITLE : DELIVERY_TITLE)
                .addData("message", message)
                .addData("decline", String.valueOf(declined))
                .addData("order", orderId.toString())
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

    private String getCourierToken(CourierDetails courier) {
        String token = courier.getMessagingToken();
        if(StringUtils.isEmpty(token)) {
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
