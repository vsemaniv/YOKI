package com.cusbee.yoki.controller;

<<<<<<< HEAD
import com.cusbee.yoki.dto.YokiResult;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.entity.enums.CrudOperation;
import com.cusbee.yoki.model.OrderModel;
import com.cusbee.yoki.service.OrderService;
import com.wordnik.swagger.annotations.ApiClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
=======
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cusbee.yoki.exception.BaseException;

/**
 * 
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
 * @author Dmytro Khodan
 * @date 09.07.2016
 * @project: yoki
 */

<<<<<<< HEAD
@ApiClass("Operations with available non-processed orders")
=======
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
@RestController
@RequestMapping(value="operator")
public class OperatorController {

<<<<<<< HEAD
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
=======
	
	public void getOrder() throws BaseException {
		
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
	}
	
}
