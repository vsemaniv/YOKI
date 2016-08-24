package com.cusbee.yoki.service.serviceimpl;


import com.cusbee.yoki.entity.Courier;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.model.OrderModel;
import com.cusbee.yoki.service.*;
import com.cusbee.yoki.utils.ErrorCodes;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cusbee.yoki.dao.OrderDao;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.entity.enums.OrderStatus;

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
	private ValidatorService validatorService;

	@Autowired
	private OrderDao orderDao;
	
	@Override
	public void acceptIncomingKitchenOrder(Long id) {
    	Order order = dao.get(id);
		order.setStatus(OrderStatus.COOKING);
		if(!order.isWrittenOff()) {
			boolean writtenOffSuccessfully = posterService.writeOffOrder(order);
			if(writtenOffSuccessfully) {
				order.setWrittenOff(true);
			} else {
				throw new ApplicationException(ErrorCodes.Order.WRITEOFF_ERROR,
						"Unexpected error during writeoff. Please contact CRM vendor");
			}
		} else {
			throw new ApplicationException(ErrorCodes.Order.WRITEOFF_ERROR,
					"Order was already written off!");
		}
    	dao.save(order);
    }

	@Override
	public void declineOrder(OrderModel request) {
		Order order = orderService.get(request.getId());
		if(StringUtils.isNotEmpty(request.getMessage())) {
			order.setMessage(request.getMessage());
		} else {
			throw new ApplicationException(ErrorCodes.Order.EMPTY_DECLINE_MESSAGE,
					"Decline message should not be empty!");
		}
		if(validatorService.isEnumValid(request.getStatus(), OrderStatus.class)) {
			order.setStatus(OrderStatus.valueOf(request.getStatus()));
		} else {
			throw new ApplicationException(ErrorCodes.Order.INVALID_STATUS,
					"Invalid order status");
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
