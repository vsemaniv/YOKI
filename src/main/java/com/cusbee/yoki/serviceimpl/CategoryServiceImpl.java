package com.cusbee.yoki.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cusbee.yoki.dao.CategoryDao;
import com.cusbee.yoki.entity.Category;
import com.cusbee.yoki.entity.CrudOperation;
import com.cusbee.yoki.entity.Dish;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.CategoryModel;
import com.cusbee.yoki.model.DishModel;
import com.cusbee.yoki.repositories.CategoryRepository;
import com.cusbee.yoki.service.CategoryService;
import com.cusbee.yoki.service.DishService;
import com.cusbee.yoki.utils.ErrorCodes;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDao dao;

	@Autowired
	private CategoryRepository repository;
	
	@Autowired
	private DishService dishService;

	@Override
	public void add(Category category) {
		dao.add(category);
	}

	@Override
	public void update(Category category) {
		dao.update(category);
	}

	@Override
	@Transactional
	public Category getById(Long id) {
		return dao.getById(id);
	}

	@Override
	public List<Category> getAll() {
		return dao.getAll();
	}

	@Override
	@Transactional
	public void remove(Long id) throws BaseException {
		Category category = getById(id);
		if(Objects.isNull(category)) {
			throw new ApplicationException(ErrorCodes.Category.EMPTY_REQUEST, "This category are not present");
		}
		List<Dish> dishes = category.getDishes();
		for(Dish dish: dishes){
			dish.setCategory(null);
		}
		this.dao.remove(category);
	}
	
	/**
	 * Method check for null pointers
	 * and if all is right, create or update Category
	 */
	public Category parse(CategoryModel request, CrudOperation status)
			throws BaseException {

		if (Objects.isNull(request)) {
			throw new ApplicationException(ErrorCodes.Category.EMPTY_REQUEST,
					"Empty Request");
		}
		
		if (Objects.isNull(request.getName())) {
			throw new ApplicationException(ErrorCodes.Category.EMPTY_FIELD,
					"Requested fields are empty");
		}

		if (!checkIfExist(request.getName())) {
			throw new ApplicationException(ErrorCodes.Category.ALREADY_EXIST,
					"This category already exists");
		}
		
		Category category = repository.findByName(request.getName());
		switch (status) {
		case CREATE:
			category = new Category();
			validateCategory(request.getName());
			category.setName(request.getName());
			return category;
		case UPDATE:
			category = dao.getById(request.getId());
			if(Objects.isNull(category)){
				throw new ApplicationException(ErrorCodes.Category.EMPTY_REQUEST, "Category with this ID is not present");
			}
			if(!Objects.isNull(request.getName())){
				validateCategory(request.getName());
				category.setName(request.getName());
			}
			return category;
		default:
			throw new ApplicationException(ErrorCodes.Category.EMPTY_REQUEST,
					"Empty Request");
		}
	}
	
	public Category removeDishFromCategory(CategoryModel request) throws BaseException  {
		if(Objects.isNull(request)){
			throw new ApplicationException(ErrorCodes.Category.EMPTY_REQUEST, "Empty Request");
		}
		if(Objects.isNull(request.getId())){
			throw new ApplicationException(ErrorCodes.Category.EMPTY_FIELD, "Category field ID is not present");
		}
		if(Objects.isNull(request.getDishes())){
			throw new ApplicationException(ErrorCodes.Category.EMPTY_FIELD, "Dishes ID's to remove is not present");
		}
		Category category = getById(request.getId());
		if(Objects.isNull(category)){
			throw new ApplicationException(ErrorCodes.Category.EMPTY_REQUEST, "Category with id:" + request.getId()+ " is not present");
		}
		List<Long> ids = new ArrayList<Long>();
		for(DishModel dish : request.getDishes()){
			ids.add(dish.getId());
		}
		List<Dish> dishes = category.getDishes();
		for(Dish dish : dishes) {
			for(Long id : ids){
				if(dish.getId()==id){
					Dish dh = dishService.getById(id);
					dh.setCategory(null);
					dishService.update(dh);
				}
			}
		}
		return category;
	}
	/**
	 * Check if this category not present
	 * @param name
	 * @return
	 * @throws BaseException
	 */
	protected boolean checkIfExist(String name) throws BaseException {
		return repository.findByName(name) == null;
	}
	
	protected boolean validateCategory(String name) throws BaseException {
		Pattern pattern = Pattern.compile("^([A-Z]){1}([a-z]){5,15}$");
		Matcher matcher = pattern.matcher(name);
		return matcher.matches();
	}
	
	@Override
	@Transactional
	public List<Dish> getAllDishes(Long id) throws BaseException {
		Category category = getById(id);
		if(Objects.isNull(category)){
			throw new ApplicationException(ErrorCodes.Dish.EMPTY_REQUEST, "Dish with id:"+ id +" is not present");
		}
		List<Dish> dishes = category.getDishes();
		return dishes;
	}

	@Override
	public Category addDishToCategory(CategoryModel request)
			throws BaseException {
		
		if(Objects.isNull(request.getId())){
			throw new ApplicationException(ErrorCodes.Category.EMPTY_FIELD, "Fild id is not present");
		}
		if(Objects.isNull(request.getDishes())) {
			throw new ApplicationException(ErrorCodes.Category.EMPTY_FIELD, "You don't input no one dish to adding");
		}
		Category category = getById(request.getId());
		for(DishModel model : request.getDishes()){
			Dish dish = dishService.getById(model.getId());
			dish.setCategory(category);
			dishService.update(dish);
		}
		return category;
	}

}
