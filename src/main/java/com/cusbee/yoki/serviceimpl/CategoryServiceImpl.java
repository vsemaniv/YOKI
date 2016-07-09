package com.cusbee.yoki.serviceimpl;

import java.util.List;
import java.util.Objects;

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
import com.cusbee.yoki.repositories.CategoryRepository;
import com.cusbee.yoki.service.CategoryService;
import com.cusbee.yoki.utils.ErrorCodes;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDao dao;

	@Autowired
	private CategoryRepository repository;

	@Override
	public void add(Category category) {
		dao.add(category);
	}

	@Override
	public void update(Category category) {
		dao.update(category);
	}

	@Override
	public Category getById(Long id) {
		return dao.getById(id);
	}

	@Override
	public List<Category> getAll() {
		return dao.getAll();
	}

	@Override
	@Transactional
	public void remove(Category category) {
		category.setDishes(null);
		dao.remove(category);
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
			category.setName(request.getName());
			return category;
		case UPDATE:
			category = dao.getById(request.getId());
			category.setName(request.getName());
			return category;
		case REMOVE:
			category = dao.getById(request.getId());
			List<Dish> dishes = category.getDishes();
			category.getDishes().removeAll(dishes);
			dao.remove(category);
			return null;
		default:
			throw new ApplicationException(ErrorCodes.Category.EMPTY_REQUEST,
					"Empty Request");
		}
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

}
