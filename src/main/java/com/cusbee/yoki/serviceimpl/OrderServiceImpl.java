package com.cusbee.yoki.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cusbee.yoki.dao.OrderDao;
import com.cusbee.yoki.entity.Order;

@Service
public class OrderServiceImpl implements OrderDao{

	@Autowired
	private OrderDao dao;
	
	@Override
	public void add(Order order) {
		dao.add(order);
	}

	@Override
	public void update(Order order) {
		dao.update(order);
	}

	@Override
	public void remove(Order order) {
		dao.remove(order);
	}

	@Override
	public List<Order> getAll() {
		return dao.getAll();
	}

	@Override
	public Order get(Long id) {
		return dao.get(id);
	}

}
