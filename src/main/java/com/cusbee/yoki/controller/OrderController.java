package com.cusbee.yoki.controller;

import com.wordnik.swagger.annotations.ApiClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cusbee.yoki.dto.YokiResult;
import com.cusbee.yoki.dto.YokiResult.Status;
import com.cusbee.yoki.entity.enums.CrudOperation;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.model.OrderModel;
import com.cusbee.yoki.service.NullPointerService;
import com.cusbee.yoki.service.OrderService;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@ApiClass("Operations with orders DEPRECATED")
@RequestMapping(value="order")
public class OrderController {
	
	@Autowired
	private OrderService service;
	
	@Autowired
	private NullPointerService nullPointerService;
	
	@ApiOperation(value="create new order")
	@RequestMapping(value="create", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public YokiResult<Order> create(@RequestBody OrderModel request) {
		nullPointerService.isNull(request);
		Order order = service.saveOrder(request, CrudOperation.CREATE);
		service.save(order);
		return new YokiResult<Order>(Status.SUCCESS, "Order create successful", order);
	}
}
