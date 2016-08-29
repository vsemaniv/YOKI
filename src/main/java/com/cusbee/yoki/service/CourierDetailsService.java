package com.cusbee.yoki.service;

import com.cusbee.yoki.entity.Account;
import com.cusbee.yoki.entity.CourierDetails;
import com.cusbee.yoki.entity.Order;

import java.util.List;

public interface CourierDetailsService {

    void remove(Long id);

    CourierDetails get(Long id);

    List<CourierDetails> getAllCouriers();

    List<CourierDetails> getAvailableCouriers();

    CourierDetails updateStatus(Long id, CourierDetails.CourierStatus status);

    Order orderDelivered(Long orderId);

    CourierDetails saveCourierDetails(String username, String token);
}
