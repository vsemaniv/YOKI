package com.cusbee.yoki.service.serviceimpl;

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
	public Ingredient get(Long id) throws BaseException {
		if(Objects.isNull(id)){
			throw new ApplicationException(ErrorCodes.Ingredient.EMPTY_FIELD, "Field ID is empty");
		}
		Ingredient ingredient = dao.get(id);
		if(Objects.isNull(ingredient)){
			throw new ApplicationException(ErrorCodes.Ingredient.EMPTY_REQUEST, "Ingredient with is ID are not present");
		}
		return ingredient;
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
			if (Objects.isNull(request.getValue())) {
				throw new ApplicationException(ErrorCodes.Ingredient.EMPTY_FIELD,
						"Empty field 'weight'");
			}
			if(!validate(request.getName())){
				throw new ApplicationException(ErrorCodes.Ingredient.ALREADY_EXIST, "This ingredient already exists");
			}
			Ingredient ingredient = new Ingredient();
			ingredient.setName(request.getName());
			ingredient.setValue(request.getValue());
			ingredient.setDescription(request.getDescription());
			ingredient.setType(request.getType());
			return ingredient;
		case UPDATE:
			Ingredient ingred = repository.findById(request.getId());
			if(Objects.isNull(ingred)){
				throw new ApplicationException(ErrorCodes.Ingredient.EMPTY_REQUEST, "Ingredient with this ID is not present");
			}
			if(!Objects.isNull(request.getName())) {
				ingred.setName(request.getName());
			}
			if(!Objects.isNull(request.getValue())){
				ingred.setValue(request.getValue());	
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
	
	public Ingredient activation(Long id, CrudOperation operation) throws BaseException {
		if(Objects.isNull(id)){
			throw new ApplicationException(ErrorCodes.Common.EMPTY_REQUEST, "Empty Request");
		}
		Ingredient ingredient = repository.findById(id);
		if(Objects.isNull(ingredient)){
			throw new ApplicationException(ErrorCodes.Ingredient.EMPTY_REQUEST, "This ingredient is not present or already blocked");
		}
		switch(operation){
		case BLOCK:
			ingredient.setEnabled(Boolean.FALSE);
			return ingredient;
		case UNBLOCK:
			ingredient = get(id);
			ingredient.setEnabled(Boolean.TRUE);
			return ingredient;
		default:
			throw new ApplicationException(ErrorCodes.Common.INVALID_REQUEST, "Invalid Request");
		}
	}
}
