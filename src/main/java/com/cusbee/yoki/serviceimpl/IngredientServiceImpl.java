package com.cusbee.yoki.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cusbee.yoki.dao.IngredientDao;
import com.cusbee.yoki.entity.Ingredient;
import com.cusbee.yoki.service.IngredientService;

@Service
public class IngredientServiceImpl implements IngredientService{

	@Autowired
	private IngredientDao dao;
	
	@Override
	public void add(Ingredient ingredient) {
		dao.add(ingredient);
	}

	@Override
	public void update(Ingredient ingredient) {
		dao.update(ingredient);
	}

	@Override
	public Ingredient getById(Long id) {
		return dao.getById(id);
	}

	@Override
	public void remove(Ingredient ingredient) {
		dao.remove(ingredient);
	}

	@Override
	public List<Ingredient> getAll() {
		return dao.getAll();
	}

}
