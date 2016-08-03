package com.cusbee.yoki.service.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cusbee.yoki.entity.*;
import com.cusbee.yoki.model.ImageModel;
import com.cusbee.yoki.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cusbee.yoki.dao.DishDao;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.DishModel;
import com.cusbee.yoki.model.IngredientModel;
import com.cusbee.yoki.repositories.CategoryRepository;
import com.cusbee.yoki.repositories.DishRepository;
import com.cusbee.yoki.repositories.IngredientRepository;
import com.cusbee.yoki.utils.ErrorCodes;

@Service
@Transactional
@PropertySource("classpath:ErrorMessages.properties")
public class DishServiceImpl implements DishService {

	@Value("${not_present}")
	public String NOT_PRESENT;
	
	@Value("${id_not_present}")
	public String ID_NOT_PRESENT;
	
	@Value("${alredy_exists}")
	public String ALREADY_EXISTS;
	
	@Value("${empty_request}")
	public String EMPTY_REQUEST;
	
	@Value("${invalid_request}")
	public String INVALID_REQUEST;
	
	@Value("${invalid_dish_name}")
	public String INVALID_DISH_NAME;
	
	@Value("${invalid_dish_weight}")
	public String INVALID_DISH_WEIGHT;
	
	@Value("${invalid_dish_price}")
	public String INVALID_DISH_PRICE;

	@Autowired
	private DishDao dao;

	@Autowired
	private DishRepository repository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private NullPointerService nullPointerService;
	
	@Autowired
	private IngredientRepository ingredientRepository;

	@Autowired
	private ImageService imageService;

	@Autowired
	private ValidatorService validatorService;

	@Autowired
	private ActivationService activationService;

	@Override
	@Transactional
	public Dish get(Long id) {
		validatorService.validateRequestIdNotNull(id);
		Dish dish = dao.get(id);
		validatorService.validateEntityNotNull(dish, Dish.class);
		return dish;
	}

	@Override
	public List<Dish> getAll() {
		return dao.getAll();
	}

	@Override
	@Transactional
	public void remove(Long id) {
		Dish dish = get(id);
		dish.setCategory(null);
		List<Ingredient> ingredients = dish.getIngredients();
		dish.getIngredients().removeAll(ingredients);
		for(Ingredient ingredient : ingredients) {
			ingredient.getDishes().remove(dish);
		}
		this.dao.remove(dish);
	}


	@Override
	@Transactional
	public Dish saveDish(DishModel request, CrudOperation operation) {
		Dish dish;
		validatorService.validateDishSaveRequest(request, operation);
		switch (operation) {
		case CREATE:
			dish = new Dish();
			if(!Objects.isNull(request.getCategoryId())){
				Category category = categoryRepository.findById(request.getCategoryId());
				nullPointerService.isNull(category);
				dish.setCategory(category);
				category.getDishes().add(dish);
			}
			dish.setEnabled(Boolean.TRUE);
			break;
		case UPDATE:
			dish = dao.get(request.getId());
			break;
		default:
			throw new ApplicationException(ErrorCodes.Common.INVALID_REQUEST, INVALID_REQUEST);
		}
		dish.setName(request.getName());
		dish.setPrice(request.getPrice());
		dish.setWeight(request.getWeight());
		dish.setDescription(request.getDescription());
		dish.setType(DishType.valueOf(request.getType().toUpperCase()));
		return dao.save(dish);
	}

	@Override
	public Dish processActivation(Long id, boolean activate) {
		Dish dish = get(id);
		activationService.processActivation(dish, activate);
		return dao.save(dish);
	}
	


	@Override
	public Dish addImages(DishModel request) {
		validatorService.validateRequestNotNull(request, Dish.class);
		if (Objects.isNull(request.getId())) {
			throw new ApplicationException(ErrorCodes.Dish.EMPTY_FIELD,
					"Fild id is not present");
		}
		if (Objects.isNull(request.getImages())) {
			throw new ApplicationException(ErrorCodes.Dish.EMPTY_FIELD,
					"You don't input no one dish to adding");
		}
		Dish dish = repository.findById(request.getId());
		if(Objects.isNull(dish)){
			throw new ApplicationException(ErrorCodes.Dish.EMPTY_REQUEST, "This category are not present or blocked");
		}
		List<DishImage> images=new ArrayList<>();
		for (ImageModel model : request.getImages()) {
			DishImage dishImage = imageService.get(model.getId());
			images.add(dishImage);
		}
		dish.setImages(images);
		dao.save(dish);
		return dish;
	}

	@Override
	public Dish removeImages(DishModel request) {
		// TODO Auto-generated method stub
		return null;
	}
}
