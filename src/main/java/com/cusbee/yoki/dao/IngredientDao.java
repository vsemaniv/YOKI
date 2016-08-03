package com.cusbee.yoki.dao;

import java.util.List;

import com.cusbee.yoki.entity.Ingredient;

public interface IngredientDao {

	Ingredient save(Ingredient ingredient);
	
	Ingredient get(Long id);
	
	void remove(Ingredient ingredient);
	
	List<Ingredient> getAll();
	
}
