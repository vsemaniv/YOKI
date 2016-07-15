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
import com.cusbee.yoki.entity.Category;
import com.cusbee.yoki.entity.CrudOperation;
import com.cusbee.yoki.entity.Dish;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.CategoryModel;
import com.cusbee.yoki.service.CategoryService;
import com.cusbee.yoki.service.NullPointerService;
import com.wordnik.swagger.annotations.ApiClass;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@ApiClass(value = "Operations with dishes category")
@RestController
@RequestMapping(value = "category", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private NullPointerService nullPointerService;

	@ApiOperation(value = "create new category")
	@RequestMapping(value = "create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public YokiResult<Category> add(@RequestBody CategoryModel request)
			throws BaseException {
		Category category = categoryService
				.parse(request, CrudOperation.CREATE);
		categoryService.add(category);
		return new YokiResult<Category>(Status.SUCCESS,"New category added successful", category);
	}

	@ApiOperation(value = "update category")
	@RequestMapping(value = "update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public YokiResult<Category> update(@RequestBody CategoryModel request)
			throws BaseException {
		Category category = categoryService
				.parse(request, CrudOperation.UPDATE);
		categoryService.update(category);
		return new YokiResult<Category>(Status.SUCCESS,"Category updated successful", category);
	}

	@ApiOperation(value = "remove category")
	@RequestMapping(value = "remove/{id}", method = RequestMethod.POST)
	public YokiResult<Category> remove(
			@ApiParam(required = true, value = "The id of the category that should be removed", name = "id") @PathVariable("id") Long id)
			throws BaseException {
		nullPointerService.isNull(id);
		categoryService.remove(id);
		return new YokiResult<Category>(Status.SUCCESS,"Category removed successful", null);
	}

	@ApiOperation(value="get all categories")
	@RequestMapping(value="getAll", method=RequestMethod.GET)
	public List<Category> getAll() throws BaseException {
		List<Category> categories = categoryService.getAll();
		return categories;
	}
	
	@ApiOperation(value="get all dishes from category")
	@RequestMapping(value="getAllDisheshFromCategory/{id}", method=RequestMethod.GET)
	public List<Dish> getAllDishes(@PathVariable("id") Long id) throws BaseException {
		nullPointerService.isNull(id);
		List<Dish> dishes = categoryService.getAllDishes(id);
		return dishes;
	}
	
	@ApiOperation(value="remove dish from category")
	@RequestMapping(value="removeDishFromCategory", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public YokiResult<Category> removeDishFromCategory(@RequestBody CategoryModel request) throws BaseException {
		Category category = categoryService.removeDishFromCategory(request);
		return new YokiResult<Category>(Status.SUCCESS, "Dishes removed successful from this category", category);
	}
	
	@ApiOperation(value="add dishes to any category")
	@RequestMapping(value="/addDishes", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public YokiResult<Category> addDishes(@RequestBody CategoryModel request) throws BaseException {
		
		nullPointerService.isNull(request);
		Category category = categoryService.addDishToCategory(request);
		return new YokiResult<Category>(Status.SUCCESS, "Dishes successful added to category:" +category+".", category);
	}
}
