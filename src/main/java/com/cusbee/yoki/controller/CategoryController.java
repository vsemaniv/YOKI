package com.cusbee.yoki.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import com.cusbee.yoki.dto.YokiResult;
import com.cusbee.yoki.dto.YokiResult.Status;
import com.cusbee.yoki.entity.Category;
import com.cusbee.yoki.entity.enums.CrudOperation;
import com.cusbee.yoki.entity.Dish;
import com.cusbee.yoki.model.CategoryModel;
import com.cusbee.yoki.service.CategoryService;
import com.wordnik.swagger.annotations.ApiClass;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@ApiClass(value = "Operations with dishes category")
@RestController
@RequestMapping(value = "category", produces = MediaType.APPLICATION_JSON_VALUE)
@PropertySource("classpath:ErrorMessages.properties")
public class CategoryController {
	
	@Value("${success_request}")
	public String STATUS;
	
	@Autowired
	private CategoryService categoryService;

	@ApiOperation(value = "create new category")
	@RequestMapping(value = "create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public YokiResult<Category> add(@RequestBody CategoryModel request) {
		return new YokiResult<Category>(Status.SUCCESS, STATUS, categoryService.saveCategory(request, CrudOperation.CREATE));
	}

	@ApiOperation(value = "update category")
	@RequestMapping(value = "update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public YokiResult<Category> update(@RequestBody CategoryModel request) {
		return new YokiResult<Category>(Status.SUCCESS, STATUS, categoryService.saveCategory(request, CrudOperation.UPDATE));
	}

	@ApiOperation(value = "remove category")
	@RequestMapping(value = "remove/{id}", method = RequestMethod.GET)
	public YokiResult<Category> remove(
			@ApiParam(required = true, value = "The id of the category that should be removed", name = "id") @PathVariable("id") Long id) {
		categoryService.remove(id);
		return new YokiResult<Category>(Status.SUCCESS, STATUS, null);
	}

	@ApiOperation(value="get all categories")
	@RequestMapping(value="getAll", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody List<Category> getAll() {
		return categoryService.getAll();
	}
	
	@ApiOperation(value="get all dishes from category")
	@RequestMapping(value="getAllDisheshFromCategory/{id}", method=RequestMethod.GET)
	public List<Dish> getAllDishes(@PathVariable("id") Long id) {
		return categoryService.getAllDishes(id);
	}
	
	@ApiOperation(value="remove dish from category")
	@RequestMapping(value="removeDishFromCategory", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public YokiResult<Category> removeDishFromCategory(@RequestBody CategoryModel request) {
		return new YokiResult<Category>(Status.SUCCESS, STATUS, categoryService.removeDishFromCategory(request));
	}
	
	@ApiOperation(value="add dishes to any category")
	@RequestMapping(value="/addDishes", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public YokiResult<Category> addDishes(@RequestBody CategoryModel request) {
		return new YokiResult<Category>(Status.SUCCESS, STATUS, categoryService.addDishToCategory(request));
	}
	
	@ApiOperation(value="get category by id")
	@RequestMapping(value="get/{id}", method=RequestMethod.POST)
	public YokiResult<Category> get(@PathVariable("id")Long id) {
		return new YokiResult<Category>(Status.SUCCESS, STATUS, categoryService.get(id));
	}
	
	@ApiOperation(value="deactivate category")
	@RequestMapping(value="deactivate/{id}", method=RequestMethod.GET)
	public YokiResult<Category> deactivate(@PathVariable("id")Long id) {
		return new YokiResult<Category>(Status.SUCCESS, STATUS, categoryService.processActivation(id, false));
	}
	
	@ApiOperation(value="activate category")
	@RequestMapping(value="activate/{id}", method=RequestMethod.GET)
	public YokiResult<Category> activate(@PathVariable("id")Long id) {
		return new YokiResult<Category>(Status.SUCCESS, STATUS, categoryService.processActivation(id, true));
	}
}
