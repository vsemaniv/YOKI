package com.cusbee.yoki.service;

import java.util.List;

import com.cusbee.yoki.entity.DishQuantity;
import com.cusbee.yoki.entity.enums.CrudOperation;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.entity.enums.OrderStatus;
import com.cusbee.yoki.model.OrderModel;

public interface OrderService {

	void remove(Order order);

	List<Order> getAll();

	List<Order> getAvailableOrders();

	List<Order> getOrderHistory(String startDate, String endDate, String clientId);

	Order get(Long id);
	
	Order saveOrder(OrderModel request, CrudOperation operation);

	Order declineOrder(OrderModel request);

	Order assignCourierToOrder(OrderModel request);
	
	Order saveOrderStatus(Long id, OrderStatus status);

	Order getCurrentOrderForCourier(Long courierId);
	
}
