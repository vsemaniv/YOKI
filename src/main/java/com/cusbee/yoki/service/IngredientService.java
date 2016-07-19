package com.cusbee.yoki.service;

import java.util.List;

import com.cusbee.yoki.entity.CrudOperation;
import com.cusbee.yoki.entity.Ingredient;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.IngredientModel;

public interface IngredientService {

	void add(Ingredient ingredient);

	void update(Ingredient ingredient);

	Ingredient get(Long id) throws BaseException;

	void remove(Long id) throws BaseException;

	List<Ingredient> getAll();
	
	Ingredient parse(IngredientModel request, CrudOperation status)
			throws BaseException;
	
	Ingredient activation(Long id, CrudOperation operation) throws BaseException;

}
