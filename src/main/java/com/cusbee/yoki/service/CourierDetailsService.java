package com.cusbee.yoki.service;

import com.cusbee.yoki.entity.Account;
import com.cusbee.yoki.entity.CourierDetails;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.model.CallCourierModel;
import com.cusbee.yoki.model.CourierModel;
import com.cusbee.yoki.model.IdModel;

import java.util.List;

public interface CourierDetailsService {

    void remove(Long id);

    CourierDetails get(Long id);

    List<CourierDetails> getAllCouriers();

    List<CourierDetails> getAvailableCouriers();

    CourierDetails getCourierByUsername(String username);

    CourierDetails updateStatus(Long id, CourierDetails.CourierStatus status);

    Order orderDelivered(Long orderId);

    CourierDetails saveCourierDetails(CourierModel request);

    CourierDetails callCourierToBase(CallCourierModel request);

    CourierDetails declineCallCourierToBase(IdModel request);
}
