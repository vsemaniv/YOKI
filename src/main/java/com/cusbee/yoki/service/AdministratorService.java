package com.cusbee.yoki.service;

import com.cusbee.yoki.entity.Order;

public interface AdministratorService {

	Order passOrderToCourier(Long orderId);

	void passOrdersToCourier(Long orderId);

	void acceptIncomingKitchenOrder(Long id);
}
