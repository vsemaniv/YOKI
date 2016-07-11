package com.cusbee.yoki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cusbee.yoki.dto.YokiResult;
import com.cusbee.yoki.dto.YokiResult.Status;
import com.cusbee.yoki.entity.CrudOperation;
import com.cusbee.yoki.entity.Dish;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.DishModel;
import com.cusbee.yoki.service.DishService;
import com.cusbee.yoki.service.NullPointerService;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="dish", consumes=MediaType.APPLICATION_JSON_VALUE)
public class DishController {
	
	@Autowired
	private DishService dishService;

	@Autowired
	private NullPointerService nullPointerService;
	
	@ApiOperation(value="create new dish")
	@RequestMapping(value="create", method=RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE })
	
	public YokiResult<Dish> add(@RequestBody DishModel request) throws BaseException {
		Dish dish = dishService.parse(request, CrudOperation.CREATE); 
		dishService.add(dish);
		return new YokiResult<Dish>(Status.SUCCESS, "New dish added successful", dish);
	}
}
