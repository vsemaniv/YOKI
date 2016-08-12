package com.cusbee.yoki.dao;

import java.util.List;

import com.cusbee.yoki.entity.Dish;

public interface DishDao {

<<<<<<< HEAD
<<<<<<< HEAD
	Dish save(Dish dish);
=======
	void add(Dish dish);
	
	void update(Dish dish);
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
=======
	Dish save(Dish dish);
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
	
	void remove(Dish dish);
	
	Dish get(Long id);
	
	List<Dish> getAll();
}
