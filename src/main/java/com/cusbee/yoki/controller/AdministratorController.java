package com.cusbee.yoki.controller;

import com.cusbee.yoki.dto.YokiResult;
import com.cusbee.yoki.dto.YokiResult.Status;
import com.cusbee.yoki.entity.Courier;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.entity.enums.CrudOperation;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.OrderModel;
import com.cusbee.yoki.service.AdministratorService;
import com.cusbee.yoki.service.CourierService;
import com.cusbee.yoki.service.OrderService;
import com.wordnik.swagger.annotations.ApiClass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 
 * @author Dmytro Khodan
 * @date 25.07.2016
 * @project: yoki
 */

@ApiClass("Administrator operations")
@RestController
@RequestMapping(value="admin")
public class AdministratorController {
	
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private AdministratorService service;
    
    @Autowired
    private CourierService courierService;
    

    @RequestMapping(value = "getAllOrders", method = RequestMethod.GET)
    public List<Order> getAllOrders() {
        return orderService.getAll();
    }

    @RequestMapping(value = "getAvailableOrders", method = RequestMethod.GET)
    public List<Order> getAvailableOrders() {
        return orderService.getAvailableOrders();
    }

    @RequestMapping(value = "decline", method = RequestMethod.POST)
    public YokiResult<Order> decline(@RequestBody OrderModel request) {
        Order order = orderService.declineOrder(request);
        return new YokiResult<>(YokiResult.Status.SUCCESS, "Order status was successfully changed", order);
    }
    
    
    // new
    @RequestMapping(value="sendOrderCooking/{id}", method=RequestMethod.POST)
    public String sendToCooking(@PathVariable("id") Long id) {
        service.processIncomingKitchenOrder(id, true);
    	return "Status success updated";
    }
    
    //new
    @RequestMapping(value="rejectOrder/{id}", method=RequestMethod.POST)
    public String rejectOrderFromKitchen(@PathVariable("id") Long id) throws BaseException {
    	service.processIncomingKitchenOrder(id, false);
    	return "Order rejected successful";
    }
    
    @RequestMapping(value="setOrderToCourier", method=RequestMethod.POST)
    public YokiResult<Order> setOrderToCourier(OrderModel request) throws BaseException {
    	//push - notification
    	Order order = orderService.saveOrder(request, CrudOperation.UPDATE);
    	return new YokiResult<> (Status.SUCCESS, "Order setted to courier successful", order );
    }
    
    public YokiResult passOrderToCourier() throws BaseException {
    	// set status delivery and update order time when courier get order and set courier is busy
    	return null;
    }
    
}
