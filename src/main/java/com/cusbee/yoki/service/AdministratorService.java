package com.cusbee.yoki.service;

import com.cusbee.yoki.entity.CourierDetails;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.model.OrderModel;

public interface AdministratorService {

	Order passOrderToCourier(Long orderId, Long courierId);

	void acceptIncomingKitchenOrder(Long id);

	void declineOrder(OrderModel request);

	CourierDetails manageCourierWorkTime(Long id, boolean onPlace);
}
