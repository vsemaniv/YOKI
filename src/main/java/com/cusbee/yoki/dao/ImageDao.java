package com.cusbee.yoki.dao;
import java.util.List;

import com.cusbee.yoki.entity.DishImage;
public interface ImageDao {
	void add(DishImage dishImage);
	
	void update(DishImage dishImage);
	
	DishImage get(Long id);
	
	List<DishImage> getAll();
	
	void remove(DishImage dishImage);
}
