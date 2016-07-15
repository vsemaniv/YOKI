package com.cusbee.yoki.dao;

import java.util.List;

import com.cusbee.yoki.entity.Dish;

public interface DishDao {

	void add(Dish dish);
	
	void update(Dish dish);
	
	void remove(Dish dish);
	
	Dish get(Long id);
	
	List<Dish> getAll();
}
