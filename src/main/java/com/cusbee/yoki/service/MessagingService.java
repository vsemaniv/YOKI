package com.cusbee.yoki.service;

import com.cusbee.yoki.entity.CourierDetails;
import java.util.Date;

public interface MessagingService {
    void notifyCourier(CourierDetails courier, Date timeToTake, Date timeToDeliver);
}
