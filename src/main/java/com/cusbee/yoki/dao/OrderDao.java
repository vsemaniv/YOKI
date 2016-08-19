package com.cusbee.yoki.dao;

import java.util.Date;
import java.util.List;

import com.cusbee.yoki.entity.Order;

public interface OrderDao {

	Order save(Order order);
	
	void remove(Order order);

	List<Order> getAvailableOrders();

	List<Order> getKitchenOrders();

	List<Order> getAll();

	List<Order> getOrderHistory(String startDate, String endDate);
	
	Order get(Long id);
	
}
