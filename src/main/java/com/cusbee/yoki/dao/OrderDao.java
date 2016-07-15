package com.cusbee.yoki.dao;

import java.util.List;

import com.cusbee.yoki.entity.Order;

public interface OrderDao {

	void add(Order order);
	
	void update(Order order);
	
	void remove(Order order);
	
	List<Order> getAll();
	
	Order get(Long id);
	
}
