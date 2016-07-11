package com.cusbee.yoki.serviceimpl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cusbee.yoki.dao.IngredientDao;
import com.cusbee.yoki.entity.Ingredient;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.IngredientModel;
import com.cusbee.yoki.repositories.IngredientRepository;
import com.cusbee.yoki.service.IngredientService;

@Service
@Transactional
public class IngredientServiceImpl implements IngredientService{

	@Autowired
	private IngredientDao dao;
	
	@Autowired
	private IngredientRepository repository;
	
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

	
	protected boolean validate(String name) throws BaseException {
		return repository.findByName(name)==null;
	}

	@Override
	public List<Ingredient> parse(List<IngredientModel> request)
			throws BaseException {
		// TODO Auto-generated method stub
		return null;
	}
}
