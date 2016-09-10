package com.cusbee.yoki.controller;

import com.cusbee.yoki.dto.YokiResult;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.entity.enums.CrudOperation;
import com.cusbee.yoki.entity.enums.OrderStatus;
import com.cusbee.yoki.model.IdModel;
import com.cusbee.yoki.model.OrderModel;
import com.cusbee.yoki.model.binotel.BinotelCallRequest;
import com.cusbee.yoki.service.BinotelService;
import com.cusbee.yoki.service.OrderService;
import com.wordnik.swagger.annotations.ApiClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ApiClass("Operations which are making only by operator")
@RestController
@RequestMapping(value = "operator")
public class OperatorController {

    @Autowired
    OrderService orderService;

    @Autowired
    BinotelService binotelService;

    @RequestMapping(value = "setOrderInProgress", method = RequestMethod.POST)
    public YokiResult<Order> setOrderInProgress(@RequestBody IdModel idModel) {
        Order order = orderService.saveOrderStatus(idModel.getId(), OrderStatus.IN_PROGRESS);
        return new YokiResult<Order>(HttpStatus.OK, "Order is now in progress", order);
    }

    @RequestMapping(value = "closeOrder", method = RequestMethod.POST)
    public YokiResult<Order> closeOrder(@RequestBody IdModel idModel) {
        Order order = orderService.closeOrder(idModel.getId());
        return new YokiResult<Order>(HttpStatus.OK, "Order was successfully closed", order);
    }

    @RequestMapping(value = "makeCall", method = RequestMethod.POST)
    public YokiResult makeCall(@RequestBody BinotelCallRequest request) {
        binotelService.makeCall(request.getLine(), request.getPhone());
        return new YokiResult<>(HttpStatus.OK, "success", null);
    }
}
