package com.cusbee.yoki.controller;

import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.service.KitchenService;
import com.wordnik.swagger.annotations.ApiClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@ApiClass("Kitchen operations")
@RestController
@RequestMapping(value="kitchen")
public class KitchenController {
    @Autowired
    KitchenService kitchenService;

    @RequestMapping(value = "getOrders", method = RequestMethod.GET)
    public List<Order> getAllOrders() {
        return kitchenService.getKitchenOrders();
    }
}
