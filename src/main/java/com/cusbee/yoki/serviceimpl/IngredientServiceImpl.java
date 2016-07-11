package com.cusbee.yoki.serviceimpl;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cusbee.yoki.dao.IngredientDao;
import com.cusbee.yoki.entity.CrudOperation;
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
		return repository.findByName(name) == null;
	}

	@Override
	public List<Ingredient> parse(List<IngredientModel> request,
			CrudOperation status) throws BaseException {
		return null;
	}

	@Override
	public Ingredient parse(IngredientModel request, CrudOperation status)
			throws BaseException {

		if (Objects.isNull(request)) {
			throw new ApplicationException(ErrorCodes.Ingredient.EMPTY_REQUEST,
					"Empty Request");
		}

		if (Objects.isNull(request.getName())) {
			throw new ApplicationException(ErrorCodes.Ingredient.EMPTY_FIELD,
					"Empty field 'name'");
		}

		if (Objects.isNull(request.getWeight())) {
			throw new ApplicationException(ErrorCodes.Ingredient.EMPTY_FIELD,
					"Empty field 'weight'");
		}

		switch (status) {
		case CREATE:
			if(!validate(request.getName())){
				throw new ApplicationException(ErrorCodes.Ingredient.ALREADY_EXIST, "This ingredient already exists");
			}
			Ingredient ingredient = new Ingredient();
			ingredient.setName(request.getName());
			ingredient.setWeight(request.getWeight());
			ingredient.setDescription(request.getDescription());
			return ingredient;
		case UPDATE:
			break;
		default:
			throw new ApplicationException(ErrorCodes.Ingredient.EMPTY_REQUEST,
					"Server resolve your request");
		}
		return null;
	}
	
}
