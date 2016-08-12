package com.cusbee.yoki.dao;

import java.util.List;

import com.cusbee.yoki.entity.Ingredient;

public interface IngredientDao {

<<<<<<< HEAD
	Ingredient save(Ingredient ingredient);
=======
	void add(Ingredient ingredient);
	
	void update(Ingredient ingredient);
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
	
	Ingredient get(Long id);
	
	void remove(Ingredient ingredient);
	
	List<Ingredient> getAll();
	
}
