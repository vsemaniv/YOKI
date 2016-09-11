package com.cusbee.yoki.service.serviceimpl;

import com.cusbee.yoki.entity.CourierDetails;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cusbee.yoki.dao.OrderDao;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.entity.enums.OrderStatus;

import java.util.Calendar;
import java.util.List;

@Service
public class AdministratorServiceImpl implements AdministratorService {

	@Autowired
	private OrderDao dao;

	@Autowired
	private StorageService posterService;

	@Autowired
	private CourierDetailsService courierService;

	@Autowired
	private OrderService orderService;

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
				throw new ApplicationException(HttpStatus.BAD_REQUEST,
						"Unexpected error during writeoff. Please contact CRM vendor");
			}
		} else {
			throw new ApplicationException(HttpStatus.BAD_REQUEST,
					"Order was already written off!");
		}
    	dao.save(order);
    }

	public Order passOrderToCourier(Long orderId) {
		Order order = orderService.get(orderId);
		CourierDetails courierDetails = order.getCourierDetails();
		if(courierDetails == null) {
			throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, "You should assign courier before passing it to him!");
		}
		order.setStatus(OrderStatus.DELIVERY);
		order.setTimeTaken(Calendar.getInstance());
		return orderDao.save(order);
	}

	public void passOrdersToCourier(Long courierId) {
		CourierDetails courier = courierService.get(courierId);
        List<Order> courierPendingOrders = orderService.getCourierPendingOrders(courier);
        if(courierPendingOrders.isEmpty()) {
            throw new ApplicationException(HttpStatus.OK, "There are no orders for courier");
        }
		courier.setStatus(CourierDetails.CourierStatus.BUSY);
		courier.setTimeToArrive(null);
		Calendar currentTime = Calendar.getInstance();
        for(Order pendingOrder : courierPendingOrders) {
            pendingOrder.setStatus(OrderStatus.DELIVERY);
            pendingOrder.setTimeTaken(currentTime);
            orderDao.save(pendingOrder);
        }
    }

}
