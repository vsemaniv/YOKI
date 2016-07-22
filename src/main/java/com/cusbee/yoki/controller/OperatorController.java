package com.cusbee.yoki.controller;

import com.cusbee.yoki.dto.YokiResult;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.model.OrderModel;
import com.cusbee.yoki.service.OperatorService;
import com.wordnik.swagger.annotations.ApiClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cusbee.yoki.exception.BaseException;

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
	OperatorService operatorService;

	@RequestMapping(value = "getAll", method = RequestMethod.GET)
	public List<Order> getAvailableOrders() throws BaseException {
		return operatorService.getAllNonProcessedOrders();
	}

	@RequestMapping(value = "processOrder", method = RequestMethod.POST)
	public YokiResult<Order> processOrder(@RequestBody OrderModel request, @RequestParam boolean accept) throws BaseException {
		Order order = operatorService.processOrder(request, accept);
		return new YokiResult<>(YokiResult.Status.SUCCESS, "Order was successfully processed", order);
	}
	
}
