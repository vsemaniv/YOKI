package com.cusbee.yoki.dao;

import java.util.List;

import com.cusbee.yoki.entity.Order;

public interface OrderDao {

	void save(Order order);
	
	void remove(Order order);
	
	List<Order> getAll();
	
	Order get(Long id);
	
}
