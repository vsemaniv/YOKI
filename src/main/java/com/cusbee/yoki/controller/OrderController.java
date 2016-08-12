package com.cusbee.yoki.controller;

<<<<<<< HEAD
import com.wordnik.swagger.annotations.ApiClass;
=======
import java.util.List;

>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cusbee.yoki.dto.YokiResult;
import com.cusbee.yoki.dto.YokiResult.Status;
<<<<<<< HEAD
import com.cusbee.yoki.entity.enums.CrudOperation;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.model.OrderModel;
=======
import com.cusbee.yoki.entity.CrudOperation;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.OrderModel;
import com.cusbee.yoki.repositories.OrderRepository;
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
import com.cusbee.yoki.service.NullPointerService;
import com.cusbee.yoki.service.OrderService;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
<<<<<<< HEAD
@ApiClass("Operations with orders DEPRECATED")
@RequestMapping(value="order")
public class OrderController {
=======
@RequestMapping(value="order")
public class OrderController {

	@Autowired
	private OrderRepository repository;
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
	
	@Autowired
	private OrderService service;
	
	@Autowired
	private NullPointerService nullPointerService;
	
	@ApiOperation(value="create new order")
	@RequestMapping(value="create", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
<<<<<<< HEAD
	public YokiResult<Order> create(@RequestBody OrderModel request) {
		nullPointerService.isNull(request);
		Order order = service.saveOrder(request, CrudOperation.CREATE);
		service.save(order);
		return new YokiResult<Order>(Status.SUCCESS, "Order create successful", order);
=======
	public YokiResult<Order> create(@RequestBody OrderModel request) throws BaseException {
		nullPointerService.isNull(request);
		Order order = service.parse(request, CrudOperation.CREATE);
		service.add(order);
		return new YokiResult<Order>(Status.SUCCESS, "Order create successful", order);
	} 
	
	@ApiOperation(value="get all orders for operator")
	@RequestMapping(value="getUnchekedOrders", method=RequestMethod.GET)
	public List<Order> getOperatorOrders() throws BaseException {
		return repository.findAllOperatorOrders();
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
	}
}
