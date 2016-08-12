package com.cusbee.yoki.controller;

<<<<<<< HEAD
import com.cusbee.yoki.dto.YokiResult;
import com.cusbee.yoki.entity.Assignment;
import com.cusbee.yoki.entity.Courier;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.service.CourierService;
import com.wordnik.swagger.annotations.ApiClass;
import org.springframework.beans.factory.annotation.Autowired;
=======
import java.util.List;

import com.cusbee.yoki.dto.YokiResult;
import com.cusbee.yoki.dto.YokiResult.Status;
import com.cusbee.yoki.entity.Courier;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.service.CourierService;
import com.wordnik.swagger.annotations.ApiClass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
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
<<<<<<< HEAD
}
=======
    
    @RequestMapping(value="getAllFreeCourier", method=RequestMethod.GET)
    public List<Courier> getAllAvailableCourier() {
    	return courierService.getAllAvailableCourier();
    }
    
    
    public YokiResult orderDone() throws BaseException {
    	// status DONE and time when courier done this order and status courier in free
    	return null;
    }
    
    @RequestMapping(value="setCourierStatus/{id}", method=RequestMethod.POST)
    public YokiResult<Courier> setCourierAvailableStatus(@PathVariable("id") Long id, Courier.CourierStatus status) throws BaseException {
    	Courier courier = courierService.processActivation(id, status);
    	return new YokiResult<Courier>(Status.SUCCESS, "Courier status successful changed", courier);
    }
 }
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
