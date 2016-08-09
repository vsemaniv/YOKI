package com.cusbee.yoki.service;

import java.util.List;

import com.cusbee.yoki.entity.Category;
import com.cusbee.yoki.entity.enums.CrudOperation;
import com.cusbee.yoki.entity.Dish;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.CategoryModel;

public interface CategoryService {

	Category get(Long id);

	List<Category> getAll();

	void remove(Long id);
	
	Category saveCategory(CategoryModel request, CrudOperation status)
			throws BaseException;
	
	List<Dish> getAllDishes(Long id);
	
	Category removeDishFromCategory(CategoryModel request) ;

	Category addDishToCategory(CategoryModel request);
	
	Category processActivation(Long id, boolean activate);
}
