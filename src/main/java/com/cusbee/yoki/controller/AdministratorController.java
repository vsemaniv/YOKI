package com.cusbee.yoki.controller;

import com.cusbee.yoki.dto.YokiResult;
import com.cusbee.yoki.dto.YokiResult.Status;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.entity.enums.CrudOperation;
import com.cusbee.yoki.model.IdModel;
import com.cusbee.yoki.model.OrderModel;
import com.cusbee.yoki.service.AdministratorService;
import com.cusbee.yoki.service.OrderService;
import com.wordnik.swagger.annotations.ApiClass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ApiClass("Administrator operations")
@RestController
@RequestMapping(value = "admin")
public class AdministratorController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private AdministratorService service;

    @RequestMapping(value = "getAllOrders", method = RequestMethod.GET)
    public List<Order> getAllOrders() {
        return orderService.getAll();
    }

    @RequestMapping(value = "getAvailableOrders", method = RequestMethod.GET)
    public List<Order> getAvailableOrders() {
        return orderService.getAvailableOrders();
    }

    @RequestMapping(value = "getOrderHistory", params = {"startDate", "endDate", "client"}, method = RequestMethod.GET)
    public List<Order> getOrderHistory(String startDate, String endDate, String client) {
        return orderService.getOrderHistory(startDate, endDate, client);
    }

    /*
    @RequestMapping(value = "decline", method = RequestMethod.POST)
    public YokiResult<Order> decline(@RequestBody OrderModel request) {
        Order order = orderService.declineOrder(request);
        return new YokiResult<>(YokiResult.Status.SUCCESS, "Order status was successfully changed", order);
    }*/

    // new
    @RequestMapping(value = "proceedToCooking", method = RequestMethod.POST)
    public String sendToCooking(@RequestBody IdModel idModel) {
        service.processIncomingKitchenOrder(idModel.getId(), true);
        return "Status success updated";
    }

    //new
    @RequestMapping(value = "cantPrepare", method = RequestMethod.POST)
    public String cantPrepare(@RequestBody IdModel idModel) {
        service.processIncomingKitchenOrder(idModel.getId(), false);
        return "Order rejected successful";
    }

    @RequestMapping(value = "setOrderToCourier", method = RequestMethod.POST)
    public YokiResult<Order> setOrderToCourier(OrderModel request) {
        //push - notification
        Order order = orderService.saveOrder(request, CrudOperation.UPDATE);
        return new YokiResult<>(Status.SUCCESS, "Courier was successfully assigned to order", order);
    }

    @RequestMapping(value = "passOrderToCourier", method = RequestMethod.POST)
    public YokiResult passOrderToCourier(@RequestBody OrderModel orderModel) {
        // set status delivery and update order time when courier get order and set courier is busy
        Order order = service.passOrderToCourier(orderModel.getId(), orderModel.getCourierId());
        return new YokiResult<>(Status.SUCCESS, "Order was successfully passed to courier", order);
    }

    @RequestMapping(value = "releaseCourier", method = RequestMethod.POST)
    public YokiResult releaseCourier(@RequestBody IdModel courierIdModel) {
        return new YokiResult<>(Status.SUCCESS, "Courier is now out of work", service.manageCourierWorkTime(courierIdModel.getId(), false));
    }

    @RequestMapping(value = "courierOnPlace", method = RequestMethod.POST)
    public YokiResult courierOnPlace(@RequestBody IdModel courierIdModel) {
        return new YokiResult<>(Status.SUCCESS, "Courier is now working", service.manageCourierWorkTime(courierIdModel.getId(), true));
    }

}
