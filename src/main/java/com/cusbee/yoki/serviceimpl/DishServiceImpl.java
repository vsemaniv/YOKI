package com.cusbee.yoki.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cusbee.yoki.dao.DishDao;
import com.cusbee.yoki.entity.CrudOperation;
import com.cusbee.yoki.entity.Dish;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.DishModel;
import com.cusbee.yoki.repositories.DishRepository;
import com.cusbee.yoki.service.CategoryService;
import com.cusbee.yoki.service.DishService;
import com.cusbee.yoki.service.IngredientService;

@Service
@Transactional
public class DishServiceImpl implements DishService {

	@Autowired
	private DishDao dao;

	@Autowired
	private DishRepository repository;

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private IngredientService ingredientService;

	@Override
	public void add(Dish dish) {
		this.dao.add(dish);
	}

	@Override
	public void update(Dish dish) {
		this.dao.update(dish);
	}

	@Override
	public void remove(Dish dish) {
		this.dao.remove(dish);
	}

	@Override
	public Dish getById(Long id) {
		return this.dao.getById(id);
	}

	@Override
	public List<Dish> getAll() {
		return this.dao.getAll();
	}

	
	protected boolean validate(String name) throws BaseException {
		return repository.findByName(name) == null;
	}

	@Override
	public Dish parse(DishModel request, CrudOperation operation)
			throws BaseException {
		// TODO Auto-generated method stub
		return null;
	}
}
