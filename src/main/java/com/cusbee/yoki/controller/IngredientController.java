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
@RequestMapping(value="ingredient", consumes=MediaType.APPLICATION_JSON_VALUE)
public class IngredientController {

	@Autowired
	private IngredientService ingredientService;
	
	@Autowired
	private IngredientRepository ingredientRepository;
	
	@Autowired
	private NullPointerService nullPointerService;
	
	@ApiOperation(value="method create new ingredient")
	@RequestMapping(value="create", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public YokiResult<Ingredient> add(@RequestBody IngredientModel request) throws BaseException {
		
		Ingredient ingredient = ingredientService.parse(request, CrudOperation.CREATE);
		ingredientService.add(ingredient);
		return new YokiResult<Ingredient>(Status.SUCCESS, "Ingredient addes successful ", ingredient);
	}
	
	@ApiOperation(value="update ingredients")
	@RequestMapping(value="update", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public YokiResult<Ingredient> update(@RequestBody IngredientModel request) throws BaseException {
		Ingredient ingredient = ingredientService.parse(request, CrudOperation.UPDATE);
		ingredientService.update(ingredient);
		return new YokiResult<Ingredient>(Status.SUCCESS, "Ingredient updated successful", ingredient);
	}
	
	@ApiOperation(value="remove ingredient")
	@RequestMapping(value="remove/{id}", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public YokiResult<Ingredient> remove(@PathVariable("id") Long id) throws BaseException {
		nullPointerService.isNull(id);
		Ingredient ingredient = ingredientService.getById(id);
		nullPointerService.isNull(ingredient);
		ingredientService.remove(ingredient);
		return new YokiResult<Ingredient>(Status.SUCCESS,"Ingredient successful removed", null);
	}
	
	@ApiOperation(value="get all ingredients")
	@RequestMapping(value="getAll", method=RequestMethod.GET)
	public List<Ingredient> getAll() throws BaseException {
		List<Ingredient> ingredients = ingredientService.getAll();
		return ingredients; 
	}
}
