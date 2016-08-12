package com.cusbee.yoki.service;

import java.util.List;

import com.cusbee.yoki.entity.Category;
<<<<<<< HEAD
import com.cusbee.yoki.entity.enums.CrudOperation;
=======
import com.cusbee.yoki.entity.CrudOperation;
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
import com.cusbee.yoki.entity.Dish;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.CategoryModel;

public interface CategoryService {

<<<<<<< HEAD
	Category get(Long id);

	List<Category> getAll();

	void remove(Long id);
	
	Category saveCategory(CategoryModel request, CrudOperation status)
			throws BaseException;
	
	List<Dish> getAllDishes(Long id);
	
	Category removeDishFromCategory(CategoryModel request) ;

	Category addDishToCategory(CategoryModel request);
	
	Category processActivation(Long id, boolean activate);
=======
	void add(Category category);

	void update(Category category);

	Category get(Long id) throws BaseException;

	List<Category> getAll();

	void remove(Long id) throws BaseException;
	
	Category parse(CategoryModel request, CrudOperation status)
			throws BaseException;
	
	List<Dish> getAllDishes(Long id) throws BaseException;
	
	Category removeDishFromCategory(CategoryModel request) throws BaseException ;

	Category addDishToCategory(CategoryModel request) throws BaseException;
	
	Category activation(Long id, CrudOperation operation) throws BaseException;
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
}
