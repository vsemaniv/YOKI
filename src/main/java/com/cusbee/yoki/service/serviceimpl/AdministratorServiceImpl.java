package com.cusbee.yoki.service.serviceimpl;


import com.cusbee.yoki.entity.Courier;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.service.CourierService;
import com.cusbee.yoki.service.OrderService;
import com.cusbee.yoki.service.StorageService;
import com.cusbee.yoki.utils.ErrorCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cusbee.yoki.dao.OrderDao;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.entity.enums.OrderStatus;
import com.cusbee.yoki.service.AdministratorService;

import java.util.Calendar;

@Service
public class AdministratorServiceImpl implements AdministratorService {

	@Autowired
	private OrderDao dao;

	@Autowired
	private StorageService posterService;

	@Autowired
	private CourierService courierService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderDao orderDao;
	
	@Override
	public void processIncomingKitchenOrder(Long id, boolean accept) {
    	Order order = dao.get(id);
		order.setStatus(OrderStatus.COOKING);
		if(accept && !order.isWrittenOff()) {
			boolean writtenOffSuccessfully = posterService.writeOffOrder(order);
			if(writtenOffSuccessfully) {
				order.setWrittenOff(true);
			} else {
				throw new ApplicationException(ErrorCodes.Order.ERROR_DURING_WRITEOFF,
						"Unexpected error during writeoff. Please contact CRM vendor");
			}
		} else {
			order.setStatus(OrderStatus.CANT_PREPARE);
		}
    	dao.save(order);
    }

	public Order passOrderToCourier(Long orderId, Long courierId) {
		Order order = orderService.get(orderId);
		Courier courier = courierService.updateStatus(courierId, Courier.CourierStatus.BUSY);
		order.setStatus(OrderStatus.DELIVERY);
		order.setTimeTaken(Calendar.getInstance());
		order.setCourier(courier);
		return orderDao.save(order);
	}

	public Courier manageCourierWorkTime(Long id, boolean onPlace) {
		return courierService.updateStatus(id, onPlace ? Courier.CourierStatus.FREE : Courier.CourierStatus.OUT);
	}

}
