package com.cusbee.yoki.service;

import java.util.List;

<<<<<<< HEAD
<<<<<<< HEAD
import com.cusbee.yoki.entity.enums.CrudOperation;
import com.cusbee.yoki.entity.Dish;
=======
import com.cusbee.yoki.entity.CrudOperation;
import com.cusbee.yoki.entity.Dish;
import com.cusbee.yoki.exception.BaseException;
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
=======
import com.cusbee.yoki.entity.enums.CrudOperation;
import com.cusbee.yoki.entity.Dish;
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
import com.cusbee.yoki.model.DishModel;

public interface DishService {

<<<<<<< HEAD
<<<<<<< HEAD
	void remove(Long id);

	Dish get(Long id);

	List<Dish> getAll();
	
	Dish saveDish(DishModel request, CrudOperation operation);

	Dish processActivation(Long id, boolean activate);

	Dish addImages(DishModel request);

	Dish removeImages(DishModel request);
=======
	void add(Dish dish);
=======
	void remove(Long id);
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79

	Dish get(Long id);

	List<Dish> getAll();
	
<<<<<<< HEAD
	Dish parse(DishModel request, CrudOperation operation)
			throws BaseException;
	
	Dish addIngredients(DishModel request) throws BaseException;
	
	Dish removeIngredients(DishModel request) throws BaseException;
	
	Dish activation(Long id, CrudOperation operation) throws BaseException;
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
=======
	Dish saveDish(DishModel request, CrudOperation operation);

	Dish processActivation(Long id, boolean activate);

	Dish addImages(DishModel request);

	Dish removeImages(DishModel request);
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
}
