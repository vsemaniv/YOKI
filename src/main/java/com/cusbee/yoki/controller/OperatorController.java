package com.cusbee.yoki.controller;

import com.cusbee.yoki.dto.YokiResult;
import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.entity.enums.CrudOperation;
import com.cusbee.yoki.entity.enums.OrderStatus;
import com.cusbee.yoki.model.IdModel;
import com.cusbee.yoki.model.OrderModel;
import com.cusbee.yoki.model.binotel.BinotelPostModel;
import com.cusbee.yoki.service.OrderService;
import com.wordnik.swagger.annotations.ApiClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.POST;
import java.util.List;

/**
 *
 * @author Dmytro Khodan
 * @date 09.07.2016
 * @project: yoki
 */

@ApiClass("Operations with available non-processed orders")
@RestController
@RequestMapping(value="operator")
public class OperatorController {

	@Autowired
	OrderService orderService;
	
	@RequestMapping(value = "getAll", method = RequestMethod.GET)
	public List<Order> getAvailableOrders() {
		return orderService.getAll();
	}

	@RequestMapping(value = "createOrder", method = RequestMethod.POST)
	public YokiResult<Order> createOrder(@RequestBody OrderModel request) {
		Order order = orderService.saveOrder(request, CrudOperation.CREATE);
		return new YokiResult<>(HttpStatus.OK, "Order was successfully created", order);
	}

	@RequestMapping(value = "updateOrder", method = RequestMethod.POST)
	public YokiResult<Order> updateOrder(@RequestBody OrderModel request) {
		Order order = orderService.saveOrder(request, CrudOperation.UPDATE);
		return new YokiResult<>(HttpStatus.OK, "Order was successfully updated", order);
	}
	
	@RequestMapping(value="declineOrder", method=RequestMethod.POST)
	public YokiResult<Order> declineOrder(@RequestBody OrderModel request) {
		Order order = orderService.declineOrder(request);
		return new YokiResult<Order>(HttpStatus.OK, "Order declined successfully", order);
	}


	@RequestMapping(value="setOrderInProgress", method=RequestMethod.POST)
	public YokiResult<Order> setOrderInProgress(@RequestBody IdModel idModel) {
		Order order = orderService.saveOrderStatus(idModel.getId(), OrderStatus.IN_PROGRESS);
		return new YokiResult<Order>(HttpStatus.OK, "Order is now in progress", order);
	}

	@RequestMapping(value="closeOrder", method=RequestMethod.POST)
	public YokiResult<Order> closeOrder(@RequestBody IdModel idModel) {
		Order order = orderService.saveOrderStatus(idModel.getId(), OrderStatus.CLOSED);
		return new YokiResult<Order>(HttpStatus.OK, "Order was successfully closed", order);
	}

	@RequestMapping(value = "testBinotel", method = RequestMethod.POST)
	public Object testBinotel(@RequestParam(value = "key") String key,
							  @RequestParam(value = "signature") String signature,
							  @RequestParam(value = "phone") String phone,
							  @RequestParam(value = "uri") String uri) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		//make rest template
		BinotelPostModel model = new BinotelPostModel(key, signature, "901", phone);
		HttpEntity<BinotelPostModel> entity = new HttpEntity<>(model, headers);
		return restTemplate.postForObject(uri, entity, Object.class);
	}
}
