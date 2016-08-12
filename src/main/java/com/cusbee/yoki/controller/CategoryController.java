package com.cusbee.yoki.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cusbee.yoki.dto.YokiResult;
import com.cusbee.yoki.dto.YokiResult.Status;
import com.cusbee.yoki.entity.Category;
<<<<<<< HEAD
<<<<<<< HEAD
import com.cusbee.yoki.entity.enums.CrudOperation;
import com.cusbee.yoki.entity.Dish;
import com.cusbee.yoki.model.CategoryModel;
import com.cusbee.yoki.service.CategoryService;
=======
import com.cusbee.yoki.entity.CrudOperation;
=======
import com.cusbee.yoki.entity.enums.CrudOperation;
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
import com.cusbee.yoki.entity.Dish;
import com.cusbee.yoki.model.CategoryModel;
import com.cusbee.yoki.service.CategoryService;
<<<<<<< HEAD
import com.cusbee.yoki.service.NullPointerService;
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
=======
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
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

<<<<<<< HEAD
<<<<<<< HEAD
	@ApiOperation(value = "create new category")
	@RequestMapping(value = "create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public YokiResult<Category> add(@RequestBody CategoryModel request) {
		return new YokiResult<Category>(Status.SUCCESS, STATUS, categoryService.saveCategory(request, CrudOperation.CREATE));
=======
	@Autowired
	private NullPointerService nullPointerService;
	
	@Autowired
	private CategoryRepository repository;

	@ApiOperation(value = "create new category")
	@RequestMapping(value = "create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public YokiResult<Category> add(@RequestBody CategoryModel request)
			throws BaseException {
		return new YokiResult<Category>(Status.SUCCESS, STATUS, categoryService.parse(request, CrudOperation.CREATE));
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
=======
	@ApiOperation(value = "create new category")
	@RequestMapping(value = "create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public YokiResult<Category> add(@RequestBody CategoryModel request) {
		return new YokiResult<Category>(Status.SUCCESS, STATUS, categoryService.saveCategory(request, CrudOperation.CREATE));
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
	}

	@ApiOperation(value = "update category")
	@RequestMapping(value = "update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
<<<<<<< HEAD
<<<<<<< HEAD
	public YokiResult<Category> update(@RequestBody CategoryModel request) {
		return new YokiResult<Category>(Status.SUCCESS, STATUS, categoryService.saveCategory(request, CrudOperation.UPDATE));
	}

	@ApiOperation(value = "remove category")
	@RequestMapping(value = "remove/{id}", method = RequestMethod.GET)
	public YokiResult<Category> remove(
			@ApiParam(required = true, value = "The id of the category that should be removed", name = "id") @PathVariable("id") Long id) {
=======
	public YokiResult<Category> update(@RequestBody CategoryModel request)
			throws BaseException {
		return new YokiResult<Category>(Status.SUCCESS, STATUS, categoryService.parse(request, CrudOperation.UPDATE));
=======
	public YokiResult<Category> update(@RequestBody CategoryModel request) {
		return new YokiResult<Category>(Status.SUCCESS, STATUS, categoryService.saveCategory(request, CrudOperation.UPDATE));
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
	}

	@ApiOperation(value = "remove category")
	@RequestMapping(value = "remove/{id}", method = RequestMethod.GET)
	public YokiResult<Category> remove(
<<<<<<< HEAD
			@ApiParam(required = true, value = "The id of the category that should be removed", name = "id") @PathVariable("id") Long id)
			throws BaseException {
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
=======
			@ApiParam(required = true, value = "The id of the category that should be removed", name = "id") @PathVariable("id") Long id) {
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
		categoryService.remove(id);
		return new YokiResult<Category>(Status.SUCCESS, STATUS, null);
	}

	@ApiOperation(value="get all categories")
	@RequestMapping(value="getAll", method=RequestMethod.GET)
<<<<<<< HEAD
<<<<<<< HEAD
	public List<Category> getAll() {
		return categoryService.getAll();
=======
	public List<Category> getAll() throws BaseException {
		return repository.findAll();
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
=======
	public List<Category> getAll() {
		return categoryService.getAll();
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
	}
	
	@ApiOperation(value="get all dishes from category")
	@RequestMapping(value="getAllDisheshFromCategory/{id}", method=RequestMethod.GET)
<<<<<<< HEAD
<<<<<<< HEAD
	public List<Dish> getAllDishes(@PathVariable("id") Long id) {
=======
	public List<Dish> getAllDishes(@PathVariable("id") Long id) throws BaseException {
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
=======
	public List<Dish> getAllDishes(@PathVariable("id") Long id) {
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
		return categoryService.getAllDishes(id);
	}
	
	@ApiOperation(value="remove dish from category")
	@RequestMapping(value="removeDishFromCategory", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
<<<<<<< HEAD
<<<<<<< HEAD
	public YokiResult<Category> removeDishFromCategory(@RequestBody CategoryModel request) {
=======
	public YokiResult<Category> removeDishFromCategory(@RequestBody CategoryModel request) throws BaseException {
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
=======
	public YokiResult<Category> removeDishFromCategory(@RequestBody CategoryModel request) {
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
		return new YokiResult<Category>(Status.SUCCESS, STATUS, categoryService.removeDishFromCategory(request));
	}
	
	@ApiOperation(value="add dishes to any category")
	@RequestMapping(value="/addDishes", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
<<<<<<< HEAD
<<<<<<< HEAD
	public YokiResult<Category> addDishes(@RequestBody CategoryModel request) {
=======
	public YokiResult<Category> addDishes(@RequestBody CategoryModel request) throws BaseException {
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
=======
	public YokiResult<Category> addDishes(@RequestBody CategoryModel request) {
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
		return new YokiResult<Category>(Status.SUCCESS, STATUS, categoryService.addDishToCategory(request));
	}
	
	@ApiOperation(value="get category by id")
	@RequestMapping(value="get/{id}", method=RequestMethod.POST)
<<<<<<< HEAD
<<<<<<< HEAD
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
=======
	public YokiResult<Category> get(@PathVariable("id")Long id) throws BaseException {
		nullPointerService.isNull(id);
		return new YokiResult<Category>(Status.SUCCESS, STATUS, repository.findById(id));
=======
	public YokiResult<Category> get(@PathVariable("id")Long id) {
		return new YokiResult<Category>(Status.SUCCESS, STATUS, categoryService.get(id));
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
	}
	
	@ApiOperation(value="deactivate category")
	@RequestMapping(value="deactivate/{id}", method=RequestMethod.GET)
	public YokiResult<Category> deactivate(@PathVariable("id")Long id) {
		return new YokiResult<Category>(Status.SUCCESS, STATUS, categoryService.processActivation(id, false));
	}
	
	@ApiOperation(value="activate category")
<<<<<<< HEAD
	@RequestMapping(value="activate/{id}", method=RequestMethod.POST)
	public YokiResult<Category> activate(@PathVariable("id")Long id) throws BaseException {
		Category category = categoryService.activation(id, CrudOperation.UNBLOCK);
		categoryService.update(category);
		return new YokiResult<Category>(Status.SUCCESS, STATUS, category);
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
=======
	@RequestMapping(value="activate/{id}", method=RequestMethod.GET)
	public YokiResult<Category> activate(@PathVariable("id")Long id) {
		return new YokiResult<Category>(Status.SUCCESS, STATUS, categoryService.processActivation(id, true));
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
	}
}
