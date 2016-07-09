package com.cusbee.yoki.service;

import java.util.List;

import com.cusbee.yoki.entity.Order;

public interface OrderService {

	void add(Order order);

	void update(Order order);

	void remove(Order order);

	List<Order> getAll();

	Order getById(Long id);
}
