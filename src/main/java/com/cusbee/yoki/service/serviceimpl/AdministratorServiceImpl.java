package com.cusbee.yoki.service.serviceimpl;

import com.cusbee.yoki.dto.YokiPosterResponse;
import com.cusbee.yoki.entity.CourierDetails;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.entity.enums.OrderStatus;

import java.util.Calendar;
import java.util.List;

@Service
public class AdministratorServiceImpl implements AdministratorService {

	@Autowired
	private StorageService posterService;

	@Autowired
	private CourierDetailsService courierService;

	@Autowired
	private OrderService orderService;

	@Override
	public YokiPosterResponse acceptIncomingKitchenOrder(Long id) {
    	Order order = orderService.get(id);
		order.setStatus(OrderStatus.COOKING);
		if(!order.isWrittenOff()) {
			String result = posterService.writeOffOrder(order);
			order.setWrittenOff(true);
			Order savedOrder = orderService.save(order);
			return new YokiPosterResponse(result, savedOrder);
		} else {
			throw new ApplicationException(HttpStatus.BAD_REQUEST,
					"Order was already written off!");
		}
    }

	public Order passOrderToCourier(Long orderId) {
		Order order = orderService.get(orderId);
		CourierDetails courierDetails = order.getCourier();
		if(courierDetails == null) {
			throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, "You should assign courier before passing it to him!");
		}
		order.setStatus(OrderStatus.DELIVERY);
		order.setTimeTaken(Calendar.getInstance());
		return orderService.save(order);
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
            orderService.save(pendingOrder);
        }
		courierService.save(courier);
    }

}
