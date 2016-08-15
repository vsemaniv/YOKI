package com.cusbee.yoki.service;

import com.cusbee.yoki.entity.Courier;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.entity.enums.CrudOperation;
import com.cusbee.yoki.model.CourierModel;

import java.util.List;

public interface CourierService {

    void remove(Long id);

    Courier get(Long id);

    List<Courier> getAll();

    Courier updateStatus(Long id, Courier.CourierStatus status);

    Order orderDelivered(Long orderId);
    
    List<Courier> getAllAvailableCouriers();
}
