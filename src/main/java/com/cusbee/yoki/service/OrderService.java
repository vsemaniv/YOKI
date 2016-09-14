package com.cusbee.yoki.service;

import java.util.List;

import com.cusbee.yoki.entity.CourierDetails;
import com.cusbee.yoki.entity.DishQuantity;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.entity.enums.OrderStatus;
import com.cusbee.yoki.model.OrderModel;

public interface OrderService {

	void remove(Order order);

	List<Order> getAll();

	List<Order> getAvailableOrders();

	List<DishQuantity> getDishesByOrder(Long orderId);

	List<Order> getOrderHistory(String startDate, String endDate, String clientId);

	Order get(Long id);
	
	Order createOrder(OrderModel request);

	Order updateOrder(OrderModel request);

	Order declineOrder(OrderModel request);

	Order assignCourierToOrder(OrderModel request);
	
	Order saveOrderStatus(Long id, OrderStatus status);

	Order getCurrentOrderForCourier(Long courierId);

	List<Order> getCourierPendingOrders(CourierDetails courier);

	Order closeOrder(Long id);
	
}
