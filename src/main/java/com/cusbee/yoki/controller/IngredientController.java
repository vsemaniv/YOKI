package com.cusbee.yoki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cusbee.yoki.dto.YokiResult;
import com.cusbee.yoki.dto.YokiResult.Status;
import com.cusbee.yoki.entity.CrudOperation;
import com.cusbee.yoki.entity.Ingredient;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.IngredientModel;
import com.cusbee.yoki.service.IngredientService;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="ingredient", consumes=MediaType.APPLICATION_JSON_VALUE)
public class IngredientController {

	@Autowired
	private IngredientService ingredientService;
	
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
		return new YokiResult<Ingredient>();
	}
}
