package com.cusbee.yoki.service;

import java.util.List;

import com.cusbee.yoki.entity.Ingredient;

public interface IngredientService {

	void add(Ingredient ingredient);

	void update(Ingredient ingredient);

	Ingredient getById(Long id);

	void remove(Ingredient ingredient);

	List<Ingredient> getAll();

}
