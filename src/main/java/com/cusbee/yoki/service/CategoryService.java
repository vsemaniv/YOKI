package com.cusbee.yoki.service;

import java.util.List;

import com.cusbee.yoki.entity.Category;
<<<<<<< HEAD
<<<<<<< HEAD
import com.cusbee.yoki.entity.enums.CrudOperation;
=======
import com.cusbee.yoki.entity.CrudOperation;
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
=======
import com.cusbee.yoki.entity.enums.CrudOperation;
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
import com.cusbee.yoki.entity.Dish;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.CategoryModel;

public interface CategoryService {

<<<<<<< HEAD
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
=======
	Category get(Long id);
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79

	List<Category> getAll();

	void remove(Long id);
	
	Category saveCategory(CategoryModel request, CrudOperation status)
			throws BaseException;
	
	List<Dish> getAllDishes(Long id);
	
	Category removeDishFromCategory(CategoryModel request) ;

	Category addDishToCategory(CategoryModel request);
	
<<<<<<< HEAD
	Category activation(Long id, CrudOperation operation) throws BaseException;
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
=======
	Category processActivation(Long id, boolean activate);
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
}
