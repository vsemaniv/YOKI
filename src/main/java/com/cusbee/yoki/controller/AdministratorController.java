package com.cusbee.yoki.controller;

import com.cusbee.yoki.dto.YokiPosterResponse;
import com.cusbee.yoki.dto.YokiResult;
import com.cusbee.yoki.entity.CourierDetails;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.model.CallCourierModel;
import com.cusbee.yoki.model.IdModel;
import com.cusbee.yoki.model.OrderModel;
import com.cusbee.yoki.service.AdministratorService;
import com.cusbee.yoki.service.CourierDetailsService;
import com.cusbee.yoki.service.OrderService;
import com.wordnik.swagger.annotations.ApiClass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ApiClass("Operations which are making only by administrator")
@RestController
@RequestMapping(value = "admin")
public class AdministratorController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private AdministratorService service;

    @Autowired
    private CourierDetailsService courierService;

    @RequestMapping(value = "getOrderHistory", method = RequestMethod.GET)
    public List<Order> getOrderHistory(@RequestParam(value = "startDate", required = false) String startDate,
                                       @RequestParam(value = "endDate", required = false) String endDate,
                                       @RequestParam(value = "clientPhone", required = false) String client) {
        return orderService.getOrderHistory(startDate, endDate, client);
    }

    @RequestMapping(value = "proceedToCooking", method = RequestMethod.POST)
    public YokiResult sendToCooking(@RequestBody IdModel idModel) {
        YokiPosterResponse response = service.acceptIncomingKitchenOrder(idModel.getId());
        return new YokiResult<>(HttpStatus.OK, "", response);
    }

    @Deprecated
    @RequestMapping(value = "setOrderToCourier", method = RequestMethod.POST)
    public YokiResult<Order> setOrderToCourier(@RequestBody OrderModel request) {
        Order order = orderService.assignCourierToOrder(request);
        return new YokiResult<>(HttpStatus.OK, "Courier was successfully assigned to order", order);
    }

    @Deprecated
    @RequestMapping(value = "passOrderToCourier", method = RequestMethod.POST)
    public YokiResult passOrderToCourier(@RequestBody IdModel idModel) {
        // set status delivery and update order time when courier get order and set courier is busy
        Order order = service.passOrderToCourier(idModel.getId());
        return new YokiResult<>(HttpStatus.OK, "Order was successfully passed to courier", order);
    }

    @RequestMapping(value = "passOrdersToCourier", method = RequestMethod.POST)
    public YokiResult passOrdersToCourier(@RequestBody IdModel idModel) {
        service.passOrdersToCourier(idModel.getId());
        return new YokiResult<>(HttpStatus.OK, "Order was successfully passed to courier", null);
    }

    @RequestMapping(value = "callCourierToBase", method = RequestMethod.POST)
    public YokiResult<CourierDetails> callCourierToBase(@RequestBody CallCourierModel request) {
        CourierDetails courier = courierService.callCourierToBase(request);
        return new YokiResult<>(HttpStatus.OK, "Now courier is notified about coming to base", courier);
    }

    @RequestMapping(value = "declineCallCourierToBase", method = RequestMethod.POST)
    public YokiResult<CourierDetails> declineCallCourierToBase(@RequestBody IdModel request) {
        CourierDetails courier = courierService.declineCallCourierToBase(request);
        return new YokiResult<>(HttpStatus.OK, "Now courier is notified about NOT coming to base", courier);
    }
}
