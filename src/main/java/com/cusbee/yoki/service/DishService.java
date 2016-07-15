package com.cusbee.yoki.service;

import java.util.List;

import com.cusbee.yoki.entity.CrudOperation;
import com.cusbee.yoki.entity.Dish;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.DishModel;

public interface DishService {

	void add(Dish dish);

	void update(Dish dish);

	void remove(Long id) throws BaseException;

	Dish get(Long id) throws BaseException;

	List<Dish> getAll();
	
	Dish parse(DishModel request, CrudOperation operation)
			throws BaseException;
	
	Dish addIngredients(DishModel request) throws BaseException;
}
