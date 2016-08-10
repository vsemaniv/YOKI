package com.cusbee.yoki.service;

import java.util.List;

import com.cusbee.yoki.entity.enums.CrudOperation;
import com.cusbee.yoki.entity.Dish;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.model.OrderModel;

public interface OrderService {

	void remove(Order order);

	List<Order> getAll();

	List<Order> getAvailableOrders();

	Order get(Long id);
	
	Order saveOrder(OrderModel request, CrudOperation operation);

	Order declineOrder(OrderModel request);

	Order assignCourier(OrderModel request);

	List<Dish> getDishesFromOrderModel(OrderModel request);
}
