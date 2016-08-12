package com.cusbee.yoki.dao;

import java.util.List;

import com.cusbee.yoki.entity.Category;

public interface CategoryDao {

<<<<<<< HEAD
<<<<<<< HEAD
	Category save(Category category);
=======
	void add(Category category);
	
	void update(Category category);
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
=======
	Category save(Category category);
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
	
	Category get(Long id);
	
	List<Category> getAll();
	
	void remove(Category category);
	
}
