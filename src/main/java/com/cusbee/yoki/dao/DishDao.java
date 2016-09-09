package com.cusbee.yoki.dao;

import java.util.List;

import com.cusbee.yoki.entity.Category;
import com.cusbee.yoki.entity.Dish;

public interface DishDao {

	Dish save(Dish dish);
	
	void remove(Dish dish);
	
	Dish get(Long id);
	
	List<Dish> getAll();

	List<Dish> getAvailable();

	List<Dish> getAvailable(Category category);
}
