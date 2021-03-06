package com.cusbee.yoki.dao;

import java.util.List;

import com.cusbee.yoki.entity.Category;

public interface CategoryDao {

	void add(Category category);
	
	void update(Category category);
	
	Category get(Long id);
	
	List<Category> getAll();
	
	void remove(Category category);
	
}
