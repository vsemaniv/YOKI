package com.cusbee.yoki.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cusbee.yoki.dao.DishDao;
import com.cusbee.yoki.entity.Dish;

@Service
public class DishServiceImpl implements DishService {

	@Autowired
	private DishDao dishDao;
	
	public void add(Dish dish) {
		dishDao.add(dish);
	}

}
