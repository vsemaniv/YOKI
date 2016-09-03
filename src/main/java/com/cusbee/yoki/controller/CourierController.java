package com.cusbee.yoki.controller;

import java.util.List;

import com.cusbee.yoki.dto.YokiResult;
import com.cusbee.yoki.dto.YokiResult.Status;
import com.cusbee.yoki.entity.CourierDetails;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.model.CourierModel;
import com.cusbee.yoki.model.IdModel;
import com.cusbee.yoki.repositories.OrderRepository;
import com.cusbee.yoki.service.CourierDetailsService;
import com.cusbee.yoki.service.OrderService;
import com.cusbee.yoki.service.ValidatorService;
import com.wordnik.swagger.annotations.ApiClass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@ApiClass("Courier controller")
@RequestMapping(value = "courier")
public class CourierController {

    @Autowired
    CourierDetailsService courierService;

    @Autowired
    OrderService orderService;

    @RequestMapping(value="getFreeCouriers", method=RequestMethod.GET)
    public List<CourierDetails> getAvailableCouriers() {
    	return courierService.getAvailableCouriers();
    }

    @RequestMapping(value="getAllCouriers", method=RequestMethod.GET)
     public List<CourierDetails> getAllCouriers() {
        return courierService.getAllCouriers();
    }

    @RequestMapping(value="getCourierByUsername", method=RequestMethod.GET)
    public CourierDetails getCourierByUsername(@RequestParam(value = "username") String username) {
        return courierService.getCourierByUsername(username);
    }

    @RequestMapping(value="done", method=RequestMethod.POST)
    public YokiResult<Order> orderDelivered(@RequestBody IdModel idModel) {
    	// status DONE and time when courier done this order and status courier in free
    	return new YokiResult<Order>(HttpStatus.OK, "Order is done", courierService.orderDelivered(idModel.getId()));
    }

    @RequestMapping(value = "saveMessagingToken", method = RequestMethod.POST)
    public YokiResult<CourierDetails> saveMessagingToken(@RequestBody CourierModel request) {
        CourierDetails courierDetails = courierService.saveCourierDetails(request);
        return new YokiResult<>(HttpStatus.OK, "Token was successfully saved", courierDetails);
    }

    //takes pending order for particular courier
    @RequestMapping(value = "getPendingOrder", method = RequestMethod.POST)
    public YokiResult<Order> getPendingOrder(@RequestBody IdModel courierIdModel) {
        Order order = orderService.getCurrentOrderForCourier(courierIdModel.getId());
        return new YokiResult<>(HttpStatus.OK, "Token was successfully saved", order);
    }
 }
