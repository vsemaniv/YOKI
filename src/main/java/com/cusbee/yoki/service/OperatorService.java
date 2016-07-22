package com.cusbee.yoki.service;

import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.OrderModel;

import java.util.List;

/**

 */
public interface OperatorService {

    List<Order> getAllNonProcessedOrders();

    Order processOrder(OrderModel request, boolean accept) throws BaseException;
}
