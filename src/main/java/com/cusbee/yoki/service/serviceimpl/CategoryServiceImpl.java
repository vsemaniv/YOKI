package com.cusbee.yoki.service.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.cusbee.yoki.dao.DishDao;
import com.cusbee.yoki.service.ValidatorService;
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
import com.cusbee.yoki.service.CategoryService;
import com.cusbee.yoki.service.DishService;
import com.cusbee.yoki.utils.ErrorCodes;

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

	@Override
	@Transactional
	public Category get(Long id) throws BaseException {
		validatorService.validateRequestIdNotNull(id);
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
		validatorService.validateCategory(request, status);
		Category category;
		switch (status) {
		case CREATE:
			category = new Category();
			category.setName(request.getName());
			category.setEnabled(Boolean.TRUE);
			break;
		case UPDATE:
			category = get(request.getId());
			category.setName(request.getName());
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
		Category category = get(id);
		return category.getDishes();
	}

	public Category activation(Long id, CrudOperation operation) throws BaseException {
		Category category = get(id);
		switch (operation) {
		case BLOCK:
			category.setEnabled(Boolean.FALSE);
			break;
		case UNBLOCK:
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
		validatorService.validateRequestNotNull(request);
		//TODO we should implement this logic on frontend. If someone calls it on backend, it will cause nothing, right?
		if (CollectionUtils.isEmpty(request.getDishes())) {
			throw new ApplicationException(ErrorCodes.Category.EMPTY_FIELD,
					"You passed no dish to add");
		}
		Category category = get(request.getId());
		for (DishModel model : request.getDishes()) {
			Dish dish = dishDao.get(model.getId());

			List<Dish> dishList = category.getDishes();
			if(dishList.contains(dish)) {
				//TODO log that this category already contains dish
			} else {
				dishList.add(dish);
			}
			dish.setCategory(category);
			dishService.update(dish);
		}
		return category;
	}

	public Category removeDishFromCategory(CategoryModel request)
			throws BaseException {
		validatorService.validateRequestNotNull(request);
		//TODO we should implement this logic on frontend. If someone calls it on backend, it will cause nothing, right?
		if (Objects.isNull(request.getDishes())) {
			throw new ApplicationException(ErrorCodes.Category.EMPTY_FIELD,
					"Dishes ID's to remove is not present");
		}
		Category category = get(request.getId());
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
