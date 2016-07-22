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
import com.cusbee.yoki.entity.Ingredient;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.IngredientModel;
import com.cusbee.yoki.repositories.IngredientRepository;
import com.cusbee.yoki.service.IngredientService;
import com.cusbee.yoki.service.NullPointerService;
import com.wordnik.swagger.annotations.ApiClass;
import com.wordnik.swagger.annotations.ApiOperation;

@ApiClass(value="ingredients operations methods")
@RestController
@RequestMapping(value="ingredient")
public class IngredientController {

	@Autowired
	private IngredientService service;
	
	@Autowired
	private IngredientRepository repository;
	
	@Autowired
	private NullPointerService nullPointerService;
	
	@ApiOperation(value="create new ingredient")
	@RequestMapping(value="create", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public YokiResult<Ingredient> add(@RequestBody IngredientModel request) throws BaseException {
		Ingredient ingredient = service.parse(request, CrudOperation.CREATE);
		service.add(ingredient);
		return new YokiResult<Ingredient>(Status.SUCCESS, "Ingredient addes successful ", ingredient);
	}
	
	@ApiOperation(value="update ingredients")
	@RequestMapping(value="update", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public YokiResult<Ingredient> update(@RequestBody IngredientModel request) throws BaseException {
		Ingredient ingredient = service.parse(request, CrudOperation.UPDATE);
		service.update(ingredient);
		return new YokiResult<Ingredient>(Status.SUCCESS, "Ingredient updated successful", ingredient);
	}
	
	@ApiOperation(value="remove ingredient")
	@RequestMapping(value="remove/{id}", method=RequestMethod.POST)
	public YokiResult<Ingredient> remove(@PathVariable("id") Long id) throws BaseException {
		nullPointerService.isNull(id);
		service.remove(id);
		return new YokiResult<Ingredient>(Status.SUCCESS,"Ingredient successful removed", null);
	}
	
	@ApiOperation(value="get all ingredients")
	@RequestMapping(value="getAll", method=RequestMethod.GET)
	public List<Ingredient> getAll() throws BaseException {
		List<Ingredient> ingredients = service.getAll();
		return ingredients; 
	}
	
	@ApiOperation(value="get ingredient by id")
	@RequestMapping(value="get/{id}", method=RequestMethod.GET)
	public YokiResult<Ingredient> get(@PathVariable("id")Long id) throws BaseException {
		nullPointerService.isNull(id);
		Ingredient ingredient = repository.findById(id);
		return new YokiResult<Ingredient>(Status.SUCCESS, "Successful request", ingredient);
	}
	
	@ApiOperation(value="activate ingredient")
	@RequestMapping(value="activate/{id}", method=RequestMethod.POST)
	public YokiResult<Ingredient> activate(@PathVariable("id")Long id) throws BaseException {
		Ingredient ingredient = service.activation(id, CrudOperation.UNBLOCK);
		service.update(ingredient);
		return new YokiResult<Ingredient>(Status.SUCCESS, "Ingredient successful unblocked", ingredient);
	}
	
	@ApiOperation(value="deactivate ingredient")
	@RequestMapping(value="deactivate/{id}", method=RequestMethod.POST)
	public YokiResult<Ingredient> deactivate(@PathVariable("id")Long id) throws BaseException {
		Ingredient ingredient = service.activation(id, CrudOperation.BLOCK);
		service.update(ingredient);
		return new YokiResult<Ingredient>(Status.SUCCESS, "Ingredient successful blocked", ingredient);
	}
}
