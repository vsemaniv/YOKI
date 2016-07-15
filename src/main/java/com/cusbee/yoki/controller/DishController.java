package com.cusbee.yoki.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.cusbee.yoki.repositories.DishRepository;
import com.cusbee.yoki.service.DishService;
import com.cusbee.yoki.service.NullPointerService;
import com.wordnik.swagger.annotations.ApiClass;
import com.wordnik.swagger.annotations.ApiOperation;

@ApiClass(value="operations with dish entity")
@RestController
@RequestMapping(value="dish")
public class DishController {
	
	@Autowired
	private DishService dishService;

	@Autowired
	private NullPointerService nullPointerService;
	
	@Autowired
	private DishRepository repository;
	
	@ApiOperation(value="create new dish")
	@RequestMapping(value="create", method=RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE })
	public YokiResult<Dish> add(@RequestBody DishModel request) throws BaseException {
		Dish dish = dishService.parse(request, CrudOperation.CREATE); 
		dishService.add(dish);
		return new YokiResult<Dish>(Status.SUCCESS, "New dish added successful", dish);
	}
	
	@ApiOperation(value="update dish") 
	@RequestMapping(value="update", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public YokiResult<Dish> update(@RequestBody DishModel request) throws BaseException {
		Dish dish = dishService.parse(request, CrudOperation.UPDATE);
		dishService.update(dish);
		return new YokiResult<Dish>(Status.SUCCESS, "Dish updated successful", dish);
	}
	
	@RequestMapping(value="remove/{id}", method=RequestMethod.POST)
	public YokiResult<Dish> remove(@PathVariable("id") Long id) throws BaseException {
		nullPointerService.isNull(id);
		dishService.remove(id);
		return new YokiResult<Dish>(Status.SUCCESS, "Dish removed successful", null);
	}
	
	@ApiOperation(value="add ingredient/s to dish")
	@RequestMapping(value="addIngredients", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public YokiResult<Dish> addIngredint(@RequestBody DishModel request) throws BaseException {
		nullPointerService.isNull(request);
		Dish dish = dishService.addIngredients(request);
		return new YokiResult<Dish>(Status.SUCCESS, "Ingredients added successful", dish);
	}
	
	@ApiOperation(value="get dish")
	@RequestMapping(value="get/{id}", method=RequestMethod.GET)
	public YokiResult<Dish> get(@PathVariable("id") Long id) throws BaseException {
		nullPointerService.isNull(id);
		return new YokiResult<Dish>(Status.SUCCESS, "Get dish with ID:"+id, repository.findById(id));
	}
	
	@ApiOperation(value="get all dishes")
	@RequestMapping(value="getAll", method=RequestMethod.GET)
	public List<Dish> getAll() throws BaseException {
		return repository.findAll();
	}
}
