package com.cusbee.yoki.serviceimpl;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cusbee.yoki.dao.CategoryDao;
import com.cusbee.yoki.dao.DishDao;
import com.cusbee.yoki.entity.Category;
import com.cusbee.yoki.entity.CrudOperation;
import com.cusbee.yoki.entity.Dish;
import com.cusbee.yoki.entity.DishType;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.DishModel;
import com.cusbee.yoki.repositories.DishRepository;
import com.cusbee.yoki.service.DishService;
import com.cusbee.yoki.service.IngredientService;
import com.cusbee.yoki.service.NullPointerService;
import com.cusbee.yoki.utils.ErrorCodes;

@Service
@Transactional
public class DishServiceImpl implements DishService {

	@Autowired
	private DishDao dao;

	@Autowired
	private DishRepository repository;

	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private IngredientService ingredientService;
	
	@Autowired
	private NullPointerService nullPointerService;

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
	@Transactional
	public Dish parse(DishModel request, CrudOperation operation)
			throws BaseException {

		if (Objects.isNull(request)) {
			throw new ApplicationException(ErrorCodes.Dish.EMPTY_REQUEST,
					"Empty Request");
		}
		
		Dish dish;
		
		switch (operation) {
		case CREATE:
			dish = new Dish();
			if(!validate(request.getName())){
				throw new ApplicationException(ErrorCodes.Dish.INVALID_REQUEST, "Name: " + request.getName() +" already exists");
			}
			if(!Objects.isNull(request.getName())){
				validateName(request.getName());
				dish.setName(request.getName());
			}
			if(!Objects.isNull(request.getPrice())){
				validatePrice(request.getPrice());
				dish.setPrice(request.getPrice());
			}
			if(!Objects.isNull(request.getWeight())){
				validateWeight(request.getWeight());
				dish.setWeight(request.getWeight());
			}
			if(!Objects.isNull(request.getDescription())){
				dish.setDescription(request.getDescription());
			}
			if(!Objects.isNull(request.getType())){
				dish.setType(DishType.valueOf(request.getType().toUpperCase()));
			}
			if(!Objects.isNull(request.getCategoryId())){
				Category category = categoryDao.getById(request.getCategoryId());
				nullPointerService.isNull(category);
				dish.setCategory(category);
				category.getDishes().add(dish);
			}
			return dish;
		case UPDATE:
			break;
		default:
			throw new ApplicationException(ErrorCodes.Common.INVALID_REQUEST,
					"Invalid request");
		}
		return null;
	}
	
	protected boolean validateName(String name) throws BaseException {
		Pattern patter = Pattern.compile("^([A-Z]{1}[a-z]{1,15}[\\s]{0,1})+$");
		Matcher matcher = patter.matcher(name);
		if(!matcher.matches()){
			throw new ApplicationException(ErrorCodes.Dish.ALREADY_EXISTS, "Invalid dish name");
		}
		return matcher.matches();
	}
	
	protected boolean validatePrice(Double price) throws BaseException {
		Pattern pattern = Pattern.compile("^([0-9]{2,4}[\\.,]{0,1}[0-9]{0,4})+$");
		Matcher matcher = pattern.matcher(price.toString());
		if(!matcher.matches()){
			throw new ApplicationException(ErrorCodes.Dish.INVALID_REQUEST, "Invalid price format");
		}
		return matcher.matches();
	}
	
	protected boolean validateWeight(Double weight) throws BaseException {
		Pattern pattern = Pattern.compile("^([0-9]{2,4}[\\.,]{0,1}[0-9]{0,4})+$");
		Matcher matcher = pattern.matcher(weight.toString());
		if(!matcher.matches()){
			throw new ApplicationException(ErrorCodes.Dish.INVALID_REQUEST, "Invalid weight format");
		}
		return matcher.matches();
	}
}
