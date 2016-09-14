package com.cusbee.yoki.dao;

import java.util.List;

import com.cusbee.yoki.entity.*;

public interface DishDao {

	Dish save(Dish dish);
	
	void remove(Dish dish);
	
	Dish get(Long id);

	DishImage getDishImageByLink(String link);
	
	List<Dish> getAll();

	List<Dish> getAvailable();

	List<Dish> getAvailable(Category category);

	List<DishQuantity> getDishesByOrder(Order order);
}
