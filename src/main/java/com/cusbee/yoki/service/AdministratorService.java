package com.cusbee.yoki.service;

import com.cusbee.yoki.entity.Courier;
import com.cusbee.yoki.entity.Order;

public interface AdministratorService {

	Order passOrderToCourier(Long orderId, Long courierId);

	void processIncomingKitchenOrder(Long id, boolean accept);

	Courier manageCourierWorkTime(Long id, boolean onPlace);
}
