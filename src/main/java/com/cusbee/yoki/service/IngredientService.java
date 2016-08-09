package com.cusbee.yoki.service;

import java.util.List;

import com.cusbee.yoki.entity.enums.CrudOperation;
import com.cusbee.yoki.entity.Ingredient;
import com.cusbee.yoki.model.IngredientModel;

public interface IngredientService {

	Ingredient get(Long id);

	void remove(Long id);

	List<Ingredient> getAll();
	
	Ingredient saveIngredient(IngredientModel request, CrudOperation status);

	Ingredient processActivation(Long id, boolean activate);
}
