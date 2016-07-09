package com.cusbee.yoki.service;

import java.util.List;

import com.cusbee.yoki.entity.Category;
import com.cusbee.yoki.entity.CrudOperation;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.CategoryModel;

public interface CategoryService {

	void add(Category category);

	void update(Category category);

	Category getById(Long id);

	List<Category> getAll();

	void remove(Category category);
	
	Category parse(CategoryModel request, CrudOperation status)
			throws BaseException;
}
