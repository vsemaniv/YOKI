package com.cusbee.yoki.service;

import com.cusbee.yoki.entity.CourierDetails;
import com.cusbee.yoki.entity.Order;

public interface MessagingService {
    void notifyCourier(CourierDetails courier, Order order);

    void releaseCourier(CourierDetails courier, Order order);
}
