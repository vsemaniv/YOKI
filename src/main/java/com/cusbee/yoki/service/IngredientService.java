package com.cusbee.yoki.service;

import java.util.List;

<<<<<<< HEAD
import com.cusbee.yoki.entity.enums.CrudOperation;
import com.cusbee.yoki.entity.Ingredient;
=======
import com.cusbee.yoki.entity.CrudOperation;
import com.cusbee.yoki.entity.Ingredient;
import com.cusbee.yoki.exception.BaseException;
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
import com.cusbee.yoki.model.IngredientModel;

public interface IngredientService {

<<<<<<< HEAD
	Ingredient get(Long id);

	void remove(Long id);

	List<Ingredient> getAll();
	
	Ingredient saveIngredient(IngredientModel request, CrudOperation status);

	Ingredient processActivation(Long id, boolean activate);
=======
	void add(Ingredient ingredient);

	void update(Ingredient ingredient);

	Ingredient get(Long id) throws BaseException;

	void remove(Long id) throws BaseException;

	List<Ingredient> getAll();
	
	Ingredient parse(IngredientModel request, CrudOperation status)
			throws BaseException;
	
	Ingredient activation(Long id, CrudOperation operation) throws BaseException;

>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
}
