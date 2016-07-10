package com.cusbee.yoki.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cusbee.yoki.dao.IngredientDao;
import com.cusbee.yoki.entity.Ingredient;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.IngredientModel;
import com.cusbee.yoki.repositories.IngredientRepository;
import com.cusbee.yoki.service.IngredientService;
import com.cusbee.yoki.utils.ErrorCodes;

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

	@Transactional
	public List<Ingredient> parse(List<IngredientModel> request) throws BaseException{
		if(Objects.isNull(request)) {
			throw new ApplicationException(ErrorCodes.Ingredient.EMPTY_FIELD, "Empty Request");
		}
		
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		Ingredient ingredient;
		for(IngredientModel model : request) {
			if(!Objects.isNull(model.getId())){
				ingredient = getById(model.getId());
				if(!Objects.isNull(ingredient)){
					ingredients.add(ingredient);
				}
			} else {
			ingredient = new Ingredient();
			if(!validate(model.getName())){
				throw new ApplicationException(ErrorCodes.Ingredient.ALREADY_EXIST, "Ingredient with this name already exists");
			}
			ingredient.setName(model.getName());
			ingredient.setWeight(model.getWeight());
			ingredient.setDescription(model.getDescription());
			ingredient.setDish(new ArrayList<>());
			ingredients.add(ingredient);
			}
		}
		return ingredients;
	}
	
	protected boolean validate(String name) throws BaseException {
		return repository.findByName(name)==null;
	}
}
