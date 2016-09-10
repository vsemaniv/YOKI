package com.cusbee.yoki.service;

import com.cusbee.yoki.entity.CourierDetails;
import com.cusbee.yoki.entity.Order;

public interface MessagingService {
    void summonCourier(CourierDetails courier);

    void cancelSummonCourier(CourierDetails courier);

    void notifyAboutDeclinedOrder(CourierDetails courier, Order order);
}
