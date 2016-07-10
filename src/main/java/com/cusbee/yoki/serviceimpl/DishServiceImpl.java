package com.cusbee.yoki.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cusbee.yoki.dao.DishDao;
import com.cusbee.yoki.entity.Category;
import com.cusbee.yoki.entity.CrudOperation;
import com.cusbee.yoki.entity.Dish;
import com.cusbee.yoki.entity.Ingredient;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.DishModel;
import com.cusbee.yoki.repositories.DishRepository;
import com.cusbee.yoki.service.CategoryService;
import com.cusbee.yoki.service.DishService;
import com.cusbee.yoki.service.IngredientService;
import com.cusbee.yoki.utils.ErrorCodes;

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

	@Transactional
	public Dish parse(DishModel request, CrudOperation operation)
			throws BaseException {

		if (Objects.isNull(request)) {
			throw new ApplicationException(ErrorCodes.Dish.EMPTY_REQUEST,
					"Empty Request");
		}
		if (Objects.isNull(request.getName())
				|| Objects.isNull(request.getPrice())) {
			throw new ApplicationException(ErrorCodes.Dish.EMPTY_FIELD,
					"Fields name/price can't stay empty");
		}
		
		switch (operation) {
		case CREATE:
			
			if(!validate(request.getName())) {
				throw new ApplicationException(ErrorCodes.Dish.ALREADY_EXISTS, "Dish with this name already exists");
			}
			
			Dish dish = new Dish();
			dish.setName(request.getName());
			if (!Objects.isNull(request.getCategoryId())) {
				Category category = categoryService.getById(request.getCategoryId());
				if (!Objects.isNull(category)) {
					dish.setCategory(category);
				}
			}
			dish.setDescription(request.getDescription());
			dish.setPrice(request.getPrice());
			dish.setWeight(request.getWeight());
			dish.setIngredients(new ArrayList<>());
			if(!Objects.isNull(request.getIngredients())) {
				List<Ingredient> ingredients = ingredientService.parse(request.getIngredients());
				for(Ingredient ingredient : ingredients) {
					if(dish.getIngredients().contains(ingredient)){
						continue;
					}
					dish.getIngredients().add(ingredient);
					ingredient.getDish().add(dish);
				}
			}
			return dish;
		case UPDATE:
			break;
		default:
			throw new ApplicationException(ErrorCodes.Common.EMPTY_REQUEST,
					"Emtpy request");
		}
		return null;
	}
	
	protected boolean validate(String name) throws BaseException {
		return repository.findByName(name) == null;
	}
}
