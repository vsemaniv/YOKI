package com.cusbee.yoki.service;

import java.util.List;

import com.cusbee.yoki.entity.enums.CrudOperation;
import com.cusbee.yoki.entity.Dish;
import com.cusbee.yoki.model.DishModel;

public interface DishService {

	Dish save(Dish dish);

	void remove(Long id);

	Dish get(Long id);

	List<Dish> getAll();

	List<Dish> getAvailable();

	Dish saveDish(DishModel request, CrudOperation operation);

	Dish processActivation(Long id, boolean activate);
}
