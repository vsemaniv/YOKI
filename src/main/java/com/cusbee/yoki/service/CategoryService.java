package com.cusbee.yoki.service;

import java.util.List;

import com.cusbee.yoki.entity.Category;
import com.cusbee.yoki.entity.CrudOperation;
import com.cusbee.yoki.entity.Dish;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.CategoryModel;

public interface CategoryService {

	void add(Category category);

	void update(Category category);

	Category get(Long id);

	List<Category> getAll();

	void remove(Long id) throws BaseException;
	
	Category parse(CategoryModel request, CrudOperation status)
			throws BaseException;
	
	List<Dish> getAllDishes(Long id) throws BaseException;
	
	Category removeDishFromCategory(CategoryModel request) throws BaseException ;

	Category addDishToCategory(CategoryModel request) throws BaseException;
}
