package com.cusbee.yoki.controller;

import com.cusbee.yoki.dto.YokiResult;
import com.cusbee.yoki.dto.YokiResult.Status;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.entity.enums.CrudOperation;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.OrderModel;
import com.cusbee.yoki.service.OrderService;
import com.wordnik.swagger.annotations.ApiClass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author Dmytro Khodan
 * @date 09.07.2016
 * @project: yoki
 */

@ApiClass("Operations with available non-processed orders")
@RestController
@RequestMapping(value="operator")
public class OperatorController {

	@Autowired
	OrderService orderService;
	
	@RequestMapping(value = "getAll", method = RequestMethod.GET)
	public List<Order> getAvailableOrders() {
		return orderService.getAll();
	}

	@RequestMapping(value = "createOrder", method = RequestMethod.POST)
	public YokiResult<Order> createOrder(@RequestBody OrderModel request) {
		Order order = orderService.saveOrder(request, CrudOperation.CREATE);
		return new YokiResult<>(YokiResult.Status.SUCCESS, "Order was successfully created", order);
	}

	@RequestMapping(value = "updateOrder", method = RequestMethod.POST)
	public YokiResult<Order> updateOrder(@RequestBody OrderModel request) {
		Order order = orderService.saveOrder(request, CrudOperation.UPDATE);
		return new YokiResult<>(YokiResult.Status.SUCCESS, "Order was successfully updated", order);
	}
	
	
	@RequestMapping(value="makeOrderInProgress/{id}", method=RequestMethod.GET)
	public YokiResult<Order> processOrder(@PathVariable("id") Long id) throws BaseException{
		Order order = orderService.setOrderInProgress(id);
		return new YokiResult<Order>(Status.SUCCESS, "Ordre success updated", order);
	}
	
	@RequestMapping(value="declineOrder", method=RequestMethod.POST)
	public YokiResult<Order> declineOrder(@RequestBody OrderModel request) throws BaseException {
		Order order = orderService.declineOrder(request);
		return new YokiResult<Order>(Status.SUCCESS, "Order declined successful", order);
	}
	
	@RequestMapping(value="acceptOrder", method = RequestMethod.POST)
	public YokiResult<Order> acceptOrder() throws BaseException {
		// send to kitchen
		return null;
	}
	
	public YokiResult<Order> closeOrder() throws BaseException {
		//close order after calling client if all is OK
		return null;
	}
}
