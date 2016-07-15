package com.cusbee.yoki.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cusbee.yoki.dao.IngredientDao;
import com.cusbee.yoki.entity.CrudOperation;
import com.cusbee.yoki.entity.Dish;
import com.cusbee.yoki.entity.Ingredient;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.IngredientModel;
import com.cusbee.yoki.repositories.IngredientRepository;
import com.cusbee.yoki.service.IngredientService;
import com.cusbee.yoki.utils.ErrorCodes;

@Service
@Transactional
public class IngredientServiceImpl implements IngredientService {

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
	@Transactional
	public Ingredient get(Long id) {
		return dao.get(id);
	}

	@Transactional
	public void remove(Long id) throws BaseException {
		Ingredient ingredient = get(id);
		List<Dish> dishes = ingredient.getDish();
		ingredient.getDish().removeAll(dishes);
		dao.remove(ingredient);
	}

	@Override
	public List<Ingredient> getAll() {
		return dao.getAll();
	}

	protected boolean validate(String name) throws BaseException {
		return repository.findByName(name) == null;
	}

	@Override
	public List<Ingredient> parse(List<IngredientModel> request,
			CrudOperation status) throws BaseException {
		return null;
	}

	@Override
	@Transactional
	public Ingredient parse(IngredientModel request, CrudOperation status)
			throws BaseException {

		if (Objects.isNull(request)) {
			throw new ApplicationException(ErrorCodes.Ingredient.EMPTY_REQUEST,
					"Empty Request");
		}

		switch (status) {
		case CREATE:
			if (Objects.isNull(request.getName())) {
				throw new ApplicationException(ErrorCodes.Ingredient.EMPTY_FIELD,
						"Empty field 'name'");
			}
			if (Objects.isNull(request.getWeight())) {
				throw new ApplicationException(ErrorCodes.Ingredient.EMPTY_FIELD,
						"Empty field 'weight'");
			}
			if(!validate(request.getName())){
				throw new ApplicationException(ErrorCodes.Ingredient.ALREADY_EXIST, "This ingredient already exists");
			}
			Ingredient ingredient = new Ingredient();
			ingredient.setName(request.getName());
			ingredient.setWeight(request.getWeight());
			ingredient.setDescription(request.getDescription());
			return ingredient;
		case UPDATE:
			Ingredient ingred = get(request.getId());
			if(Objects.isNull(ingred)){
				throw new ApplicationException(ErrorCodes.Ingredient.EMPTY_REQUEST, "Ingredient with this ID is not present");
			}
			if(!Objects.isNull(request.getName())) {
				ingred.setName(request.getName());
			}
			if(!Objects.isNull(request.getWeight())){
				ingred.setWeight(request.getWeight());	
			}
			if(!Objects.isNull(request.getDescription())){
				ingred.setDescription(request.getDescription());
			}
			List<Dish> dishes = ingred.getDish();
			if(Objects.isNull(dishes)){
				ingred.setDish(new ArrayList<Dish>());
			}else {
				ingred.getDish().addAll(dishes);
			}
			return ingred;
		default:
			throw new ApplicationException(ErrorCodes.Ingredient.EMPTY_REQUEST,
					"Server resolve your request");
		}
	}
	
}
