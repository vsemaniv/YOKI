package com.cusbee.yoki.dao;

import java.util.List;

import com.cusbee.yoki.entity.Order;

public interface OrderDao {

<<<<<<< HEAD
	Order save(Order order);
	
	void remove(Order order);

	List<Order> getAvailableOrders();
=======
	void add(Order order);
	
	void update(Order order);
	
	void remove(Order order);
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
	
	List<Order> getAll();
	
	Order get(Long id);
	
}
