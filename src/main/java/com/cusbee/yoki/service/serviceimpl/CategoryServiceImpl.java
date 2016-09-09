package com.cusbee.yoki.service.serviceimpl;

import java.util.*;

import com.cusbee.yoki.dao.DishDao;
import com.cusbee.yoki.service.ActivationService;
import com.cusbee.yoki.service.ValidatorService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cusbee.yoki.dao.CategoryDao;
import com.cusbee.yoki.entity.Category;
import com.cusbee.yoki.entity.enums.CrudOperation;
import com.cusbee.yoki.entity.Dish;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.model.CategoryModel;
import com.cusbee.yoki.model.DishModel;
import com.cusbee.yoki.service.CategoryService;
import com.cusbee.yoki.service.DishService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDao dao;
	
	@Autowired
	private DishDao dishDao;

	@Autowired
	private DishService dishService;

	@Autowired
	private ValidatorService validatorService;

	@Autowired
	private ActivationService activationService;

	@Override
	@Transactional
	public Category get(Long id) {
		validatorService.validateRequestIdNotNull(id, Category.class);
		Category category = dao.get(id);
		validatorService.validateEntityNotNull(category, Category.class);
		return category;
	}

	@Override
	public List<Category> getAll() {
		return dao.getAll();
	}

	@Override
	@Transactional
	public void remove(Long id) {
		Category category = get(id);
		Set<Dish> dishes = category.getDishes();
		for (Dish dish : dishes) {
			dish.setCategory(null);
		}
		this.dao.remove(category);
	}

	public Category saveCategory(CategoryModel request, CrudOperation status) {
		validatorService.validateCategorySaveRequest(request, status);
		Category category;
		switch (status) {
		case CREATE:
			category = new Category();
			category.setName(request.getName());
			category.setEnabled(Boolean.TRUE);
			break;
		case UPDATE:
			category = get(request.getId());

			break;
		default:
			throw new ApplicationException(HttpStatus.BAD_REQUEST,
					"Unsupported operation");
		}
		category.setName(request.getName());
		return dao.save(category);
	}

	@Override
	@Transactional
	public Set<Dish> getAllDishes(Long id) {
		Category category = get(id);
		return category.getDishes();
	}

	@Override
	@Transactional
	public List<Dish> getAvailableDishes(Long id) {
		Category category = get(id);
		return new ArrayList<>(dishDao.getAvailable(category));
	}

	public Category processActivation(Long id, boolean activate) {
		Category category = get(id);
		activationService.processActivation(category, activate);
		return dao.save(category);
	}

	@Override
	public Category addDishToCategory(CategoryModel request) {
		validatorService.validateRequestNotNull(request, Category.class);
		//TODO we should implement this logic on frontend. If someone calls it on backend, it will cause nothing, right?
		if (CollectionUtils.isEmpty(request.getDishes())) {
			throw new ApplicationException(HttpStatus.BAD_REQUEST,
					"You passed no dish to add");
		}
		Category category = get(request.getId());
		for (DishModel model : request.getDishes()) {
			Dish dish = dishDao.get(model.getId());

			Set<Dish> dishList = category.getDishes();
			if(dishList.contains(dish)) {
				//TODO log that this category already contains dish
			} else {
				dishList.add(dish);
			}
			dish.setCategory(category);
			dishDao.save(dish);
		}
		return category;
	}

	public Category removeDishFromCategory(CategoryModel request) {
		validatorService.validateRequestNotNull(request, Category.class);
		//TODO we should implement this logic on frontend. If someone calls it on backend, it will cause nothing, right?
		if (Objects.isNull(request.getDishes())) {
			throw new ApplicationException(HttpStatus.BAD_REQUEST,
					"Dishes ID's to remove is not present");
		}
		Category category = get(request.getId());
		List<Long> ids = new ArrayList<>();
		for (DishModel dish : request.getDishes()) {
			ids.add(dish.getId());
		}
		Set<Dish> dishes = category.getDishes();
		for (Dish dish : dishes) {
			for (Long id : ids) {
				if (dish.getId().equals(id)) {
					Dish dh = dishService.get(id);
					dh.setCategory(null);
					dishDao.save(dh);
				}
			}
		}
		return category;
	}
}
