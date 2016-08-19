package com.cusbee.yoki.controller;

import com.cusbee.yoki.dto.YokiResult;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.entity.enums.OrderStatus;
import com.cusbee.yoki.model.IdModel;
import com.cusbee.yoki.service.KitchenService;
import com.cusbee.yoki.service.OrderService;
import com.wordnik.swagger.annotations.ApiClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ApiClass("Kitchen operations")
@RestController
@RequestMapping(value="kitchen")
public class KitchenController {
    @Autowired
    KitchenService kitchenService;

    @Autowired
    OrderService orderService;

    @RequestMapping(value = "getOrders", method = RequestMethod.GET)
    public List<Order> getAllOrders() {
        return kitchenService.getKitchenOrders();
    }

    @RequestMapping(value = "orderPrepared", method = RequestMethod.POST)
    public YokiResult<Order> orderPrepared(@RequestBody IdModel orderIdModel) {
        Order order = orderService.saveOrderStatus(orderIdModel.getId(), OrderStatus.PREPARED);
        return new YokiResult<>(YokiResult.Status.SUCCESS, "Order is prepared now", order);
    }

    @RequestMapping(value = "orderNotPrepared", method = RequestMethod.POST)
    public YokiResult<Order> orderNotPrepared(@RequestBody IdModel orderIdModel) {
        Order order = orderService.saveOrderStatus(orderIdModel.getId(), OrderStatus.COOKING);
        return new YokiResult<>(YokiResult.Status.SUCCESS, "Order is on cooking again", order);
    }
}
