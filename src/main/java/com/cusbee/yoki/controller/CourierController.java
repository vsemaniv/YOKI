package com.cusbee.yoki.controller;

import com.cusbee.yoki.dto.YokiResult;
import com.cusbee.yoki.entity.Assignment;
import com.cusbee.yoki.entity.Courier;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.service.CourierService;
import com.wordnik.swagger.annotations.ApiClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ApiClass("Courier controller")
@RequestMapping(value = "courier")
public class CourierController {

    @Autowired
    CourierService courierService;

    /**
     * Looks up if there is an
     *
     * @param courierId - chosen courier id.
     * @return
     */
    @RequestMapping(value = "checkOrders", method = RequestMethod.GET)
    public YokiResult<Order> checkAssignment(Long courierId) {
        return null;
    }
}
