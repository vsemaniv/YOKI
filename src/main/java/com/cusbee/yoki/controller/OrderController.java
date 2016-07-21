package com.cusbee.yoki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cusbee.yoki.dto.YokiResult;
import com.cusbee.yoki.dto.YokiResult.Status;
import com.cusbee.yoki.entity.CrudOperation;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.OrderModel;
import com.cusbee.yoki.repositories.OrderRepository;
import com.cusbee.yoki.service.NullPointerService;
import com.cusbee.yoki.service.OrderService;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="order")
public class OrderController {

	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private OrderService service;
	
	@Autowired
	private NullPointerService nullPointerService;
	
	@ApiOperation(value="create new order")
	@RequestMapping(value="create", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public YokiResult<Order> create(@RequestBody OrderModel request) throws BaseException {
		nullPointerService.isNull(request);
		Order order = service.parse(request, CrudOperation.CREATE);
		service.add(order);
		return new YokiResult<Order>(Status.SUCCESS, "Order create successful", order);
	}
}
