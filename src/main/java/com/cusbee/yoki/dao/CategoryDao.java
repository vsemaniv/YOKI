package com.cusbee.yoki.dao;

import java.util.List;

import com.cusbee.yoki.entity.Category;

public interface CategoryDao {

<<<<<<< HEAD
	Category save(Category category);
=======
	void add(Category category);
	
	void update(Category category);
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
	
	Category get(Long id);
	
	List<Category> getAll();
	
	void remove(Category category);
	
}
