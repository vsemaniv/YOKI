package com.cusbee.yoki.controller;

import com.cusbee.yoki.dto.YokiResult;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.OrderModel;
import com.cusbee.yoki.service.KitchenService;
import com.cusbee.yoki.service.NullPointerService;
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
@RequestMapping(value="kitchen")
public class AdministratorController {
    @Autowired
    KitchenService kitchenService;

    @Autowired
    NullPointerService npService;

    @RequestMapping(value = "getAll", method = RequestMethod.GET)
    public List<Order> getKitchenOrders() {
        return kitchenService.getKitchenOrders();
    }

    @RequestMapping(value = "deliver/{id}", method = RequestMethod.POST)
    public YokiResult<Order> passToDriver(@PathVariable("id")Long id) {
        npService.isNull(id);
        Order order = kitchenService.passToDriver(id);
        return new YokiResult<>(YokiResult.Status.SUCCESS, "Order status was successfully changed", order);
    }

    @RequestMapping(value = "return/{id}", method = RequestMethod.POST)
    public YokiResult<Order> canNotPrepare(@RequestParam(value = "message") String message, @PathVariable("id")Long id) {
        npService.isNull(id);
        Order order = kitchenService.canNotPrepare(id);
        return new YokiResult<>(YokiResult.Status.SUCCESS, "Order status was successfully changed", order);
    }
}
