package com.cusbee.yoki.controller;

import com.cusbee.yoki.dto.YokiResult;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.model.OrderModel;
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
    OrderService orderService;

    @RequestMapping(value = "getAllOrders", method = RequestMethod.GET)
    public List<Order> getAllOrders() {
        return orderService.getAll();
    }

    @RequestMapping(value = "getAvailableOrders", method = RequestMethod.GET)
    public List<Order> getAvailableOrders() {
        return orderService.getAvailableOrders();
    }

    @RequestMapping(value = "assignCourier", method = RequestMethod.POST)
    public YokiResult assignCourier(OrderModel request) {
        try {
            return new YokiResult<>(YokiResult.Status.SUCCESS, "Courier was successfully assigned", orderService.assignCourier(request));
        } catch (ApplicationException e) {
            return new YokiResult<>(YokiResult.Status.SUCCESS, "Courier was successfully assigned", e.getErrorCode());
        }

    }

    @RequestMapping(value = "decline", method = RequestMethod.POST)
    public YokiResult<Order> decline(@RequestBody OrderModel request) {
        Order order = orderService.declineOrder(request);
        return new YokiResult<>(YokiResult.Status.SUCCESS, "Order status was successfully changed", order);
    }
}
