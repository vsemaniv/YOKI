package com.cusbee.yoki.controller;

import java.util.List;

import com.cusbee.yoki.dto.YokiResult;
import com.cusbee.yoki.dto.YokiResult.Status;
import com.cusbee.yoki.entity.Courier;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.entity.enums.OrderStatus;
import com.cusbee.yoki.service.CourierService;
import com.cusbee.yoki.service.OrderService;
import com.wordnik.swagger.annotations.ApiClass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ApiClass("Courier controller")
@RequestMapping(value = "courier")
public class CourierController {

    @Autowired
    CourierService courierService;

    @Autowired
    OrderService orderService;

    @RequestMapping(value="getAllFreeCourier", method=RequestMethod.GET)
    public List<Courier> getAllAvailableCouriers() {
    	return courierService.getAllAvailableCouriers();
    }

    @RequestMapping(value="done", method=RequestMethod.POST)
    public YokiResult<Order> orderDelivered(@PathVariable("id")Long orderId) {
    	// status DONE and time when courier done this order and status courier in free
    	return new YokiResult<Order>(Status.SUCCESS, "Order is done", courierService.orderDelivered(orderId));
    }
 }
