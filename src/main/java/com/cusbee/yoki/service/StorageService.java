package com.cusbee.yoki.service;

import com.cusbee.yoki.entity.Order;

public interface StorageService {
    boolean writeOffOrder(Order order);
}