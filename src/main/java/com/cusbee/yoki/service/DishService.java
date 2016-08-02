package com.cusbee.yoki.service;

import java.util.List;

import com.cusbee.yoki.entity.CrudOperation;
import com.cusbee.yoki.entity.Dish;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.DishModel;

public interface DishService {

	void save(Dish dish);

	void remove(Long id);

	Dish get(Long id);

	List<Dish> getAll();
	
	Dish parse(DishModel request, CrudOperation operation)
			throws BaseException;
	
	Dish addIngredients(DishModel request);
	
	Dish removeIngredients(DishModel request);

	public Dish processActivation(Long id, boolean activate);

	Dish addImages(DishModel request);

	Dish removeImages(DishModel request);
}
