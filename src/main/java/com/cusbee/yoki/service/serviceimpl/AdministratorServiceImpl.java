package com.cusbee.yoki.service.serviceimpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cusbee.yoki.dao.OrderDao;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.entity.enums.OrderStatus;
import com.cusbee.yoki.service.AdministratorService;

@Service
public class AdministratorServiceImpl implements AdministratorService {

	@Autowired
	private OrderDao dao;
	
	@Override
	public void setStatusCoocking(Long id) {
    	Order order = dao.get(id);
    	order.setStatus(OrderStatus.COOKING);
    	dao.save(order);
    }

	@Override
	public void rejectOrderFromKitchen(Long id) {
		Order order = dao.get(id);
		order.setStatus(OrderStatus.CANT_PREPARE);
		dao.save(order);
	}

}
