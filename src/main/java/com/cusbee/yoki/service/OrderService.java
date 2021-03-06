package com.cusbee.yoki.service;

import java.util.List;

import com.cusbee.yoki.entity.CrudOperation;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.OrderModel;

public interface OrderService {

	void add(Order order);

	void update(Order order);

	void remove(Order order);

	List<Order> getAll();

	Order get(Long id);
	
	Order parse(OrderModel request, CrudOperation operation)
			throws BaseException;
}
