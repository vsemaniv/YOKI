package com.cusbee.yoki.controller;

import com.cusbee.yoki.dto.YokiResult;
import com.cusbee.yoki.entity.DishQuantity;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.entity.enums.CrudOperation;
import com.cusbee.yoki.model.IdModel;
import com.cusbee.yoki.model.OrderModel;
import com.cusbee.yoki.service.OrderService;
import com.wordnik.swagger.annotations.ApiClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created on 11.09.2016.
 */
@ApiClass("Operations with orders")
@RestController
@RequestMapping(value = "order")
@Transactional
public class OrderController {

    @Autowired
    OrderService orderService;

    @RequestMapping(value = "getAllOrders", method = RequestMethod.GET)
    public List<Order> getAllOrders() {
        return orderService.getAll();
    }

    @RequestMapping(value = "getAvailableOrders", method = RequestMethod.GET)
    public List<Order> getAvailableOrders() {
        return orderService.getAvailableOrders();
    }

    @RequestMapping(value = "createOrder", method = RequestMethod.POST)
    public YokiResult<Order> createOrder(@RequestBody OrderModel request) {
        Order order = orderService.saveOrder(request, CrudOperation.CREATE);
        return new YokiResult<>(HttpStatus.OK, "Order was successfully created", order);
    }

    @RequestMapping(value = "updateOrder", method = RequestMethod.POST)
    public YokiResult<Order> updateOrder(@RequestBody OrderModel request) {
        Order order = orderService.saveOrder(request, CrudOperation.UPDATE);
        return new YokiResult<>(HttpStatus.OK, "Order was successfully updated", order);
    }

    @RequestMapping(value = "declineOrder", method = RequestMethod.POST)
    public YokiResult<Order> declineOrder(@RequestBody OrderModel request) {
        Order order = orderService.declineOrder(request);
        return new YokiResult<>(HttpStatus.OK, "Order was successfully declined", order);
    }

    @RequestMapping(value = "getDishes", method = RequestMethod.POST)
    public List<DishQuantity> getDishes(@RequestBody IdModel idModel) {
        return orderService.getDishesByOrder(idModel.getId());
    }


}
