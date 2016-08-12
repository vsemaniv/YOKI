package com.cusbee.yoki.controller;

<<<<<<< HEAD
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

=======
import com.cusbee.yoki.dto.YokiResult;
import com.cusbee.yoki.dto.YokiResult.Status;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.entity.enums.CrudOperation;
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.OrderModel;
import com.cusbee.yoki.service.OrderService;
import com.wordnik.swagger.annotations.ApiClass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
<<<<<<< HEAD
 * 
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
=======
 *
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
 * @author Dmytro Khodan
 * @date 09.07.2016
 * @project: yoki
 */

<<<<<<< HEAD
<<<<<<< HEAD
@ApiClass("Operations with available non-processed orders")
=======
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
=======
@ApiClass("Operations with available non-processed orders")
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
@RestController
@RequestMapping(value="operator")
public class OperatorController {

<<<<<<< HEAD
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
=======
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
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
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
