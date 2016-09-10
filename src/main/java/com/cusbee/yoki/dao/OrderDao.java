package com.cusbee.yoki.dao;

import java.util.List;

import com.cusbee.yoki.entity.CourierDetails;
import com.cusbee.yoki.entity.Order;

public interface OrderDao {

	Order save(Order order);
	
	void remove(Order order);

	List<Order> getAll();

	List<Order> getAvailable();
	
	Order get(Long id);

	List<Order> getCourierPendingOrders(CourierDetails courier);
	
}
