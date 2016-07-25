package com.cusbee.yoki.service.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cusbee.yoki.utils.Validator;
import org.apache.commons.collections.CollectionUtils;
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
import com.cusbee.yoki.repositories.DishRepository;
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
	private DishRepository dishRepository;

	@Autowired
	private DishService dishService;

	private Validator validator = Validator.getValidator();

	@Override
	@Transactional
	public Category get(Long id) throws BaseException {
		validator.validateRequestIdNotNull(id);
		Category category = dao.get(id);
		validator.validateEntityNotNull(category);
		return category;
	}

	@Override
	public List<Category> getAll() {
		return dao.getAll();
	}

	@Override
	@Transactional
	public void remove(Long id) throws BaseException {
		Category category = get(id);
		List<Dish> dishes = category.getDishes();
		for (Dish dish : dishes) {
			dish.setCategory(null);
		}
		this.dao.remove(category);
	}

	/**
	 * Method check for null pointers and if all is right, create or update
	 * Category
	 */
	public Category parseRequest(CategoryModel request, CrudOperation status)
			throws BaseException {
		validator.validateCategory(request, status);
		Category category;
		switch (status) {
		case CREATE:
			category = new Category();
			category.setName(request.getName());
			category.setEnabled(Boolean.TRUE);
			break;
		case UPDATE:
			//TODO replace repository with DAO? Replace two lines below with *get(id)* and check.
			category = repository.findById(request.getId());
			validator.validateEntityNotNull(category);
			break;
		default:
			throw new ApplicationException(ErrorCodes.Common.INVALID_REQUEST,
					"Unsupported operation");
		}
		return dao.save(category);
	}



	@Override
	@Transactional
	public List<Dish> getAllDishes(Long id) throws BaseException {
		//TODO replace repository with DAO? Replace three lines below with *get(id)* and check.
		validator.validateRequestIdNotNull(id);
		Category category = repository.findById(id);
		validator.validateEntityNotNull(category);
		List<Dish> dishes = category.getDishes();
		return dishes;
	}

	public Category activation(Long id, CrudOperation operation) throws BaseException {
		validator.validateRequestIdNotNull(id);
		Category category;
		switch (operation) {
		case BLOCK:
			category = repository.findById(id);
			validator.validateEntityNotNull(category);
			category.setEnabled(Boolean.FALSE);
			break;
		case UNBLOCK:
			category = get(id);
			validator.validateEntityNotNull(category);
			category.setEnabled(Boolean.TRUE);
			break;
		default:
			throw new ApplicationException(ErrorCodes.Common.INVALID_REQUEST,
					"Unsupported operation");
		}
		return dao.save(category);
	}

	@Override
	public Category addDishToCategory(CategoryModel request)
			throws BaseException {
		validator.validateRequestNotNull(request);
		validator.validateRequestIdNotNull(request.getId());
		//TODO we should implement this logic on frontend. If someone calls it on backend, it will cause nothing, right?
		if (CollectionUtils.isEmpty(request.getDishes())) {
			throw new ApplicationException(ErrorCodes.Category.EMPTY_FIELD,
					"You passed no dish to add");
		}
		Category category = repository.findById(request.getId());
		validator.validateEntityNotNull(category);
		for (DishModel model : request.getDishes()) {
			Dish dish = dishRepository.findById(model.getId());
			/*
			List<Dish> dishList = category.getDishes();
			if(dishList.contains(dish)) {
				//TODO log that this category already contains dish
			} else {
				dishList.add(dish);
			}*/
			dish.setCategory(category);
			dishService.update(dish);
		}
		return category;
	}

	public Category removeDishFromCategory(CategoryModel request)
			throws BaseException {
		validator.validateRequestNotNull(request);
		validator.validateRequestIdNotNull(request.getId());
		//TODO we should implement this logic on frontend. If someone calls it on backend, it will cause nothing, right?
		if (Objects.isNull(request.getDishes())) {
			throw new ApplicationException(ErrorCodes.Category.EMPTY_FIELD,
					"Dishes ID's to remove is not present");
		}
		Category category = repository.findById(request.getId());
		validator.validateEntityNotNull(category);
		List<Long> ids = new ArrayList<Long>();
		for (DishModel dish : request.getDishes()) {
			ids.add(dish.getId());
		}
		List<Dish> dishes = category.getDishes();
		for (Dish dish : dishes) {
			for (Long id : ids) {
				if (dish.getId() == id) {
					Dish dh = dishService.get(id);
					dh.setCategory(null);
					dishService.update(dh);
				}
			}
		}
		return category;
	}
}
