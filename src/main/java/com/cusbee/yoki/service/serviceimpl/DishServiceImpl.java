package com.cusbee.yoki.service.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cusbee.yoki.entity.*;
import com.cusbee.yoki.model.ImageModel;
import com.cusbee.yoki.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cusbee.yoki.dao.CategoryDao;
import com.cusbee.yoki.dao.DishDao;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.DishModel;
import com.cusbee.yoki.model.IngredientModel;
import com.cusbee.yoki.repositories.CategoryRepository;
import com.cusbee.yoki.repositories.DishRepository;
import com.cusbee.yoki.repositories.IngredientRepository;
import com.cusbee.yoki.service.DishService;
import com.cusbee.yoki.service.IngredientService;
import com.cusbee.yoki.service.NullPointerService;
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
	
	@Value("${invalid_dish_price}")
	public String VALIDATE_NAME;
	
	@Value("${invalid_dish_price}")
	public String VALIDATE_WEIGHT;
	
	@Value("${invalid_dish_price}")
	public String VALIDATE_PRICE;
	
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
	
	@Override
	public void add(Dish dish) {
		this.dao.add(dish);
	}

	@Override
	public void update(Dish dish) {
		this.dao.update(dish);
	}

	@Override
	@Transactional
	public void remove(Long id) throws BaseException {
		if(Objects.isNull(id)){
			throw new ApplicationException(ErrorCodes.Dish.EMPTY_FIELD, ID_NOT_PRESENT);
		}
		Dish dish = get(id);
		if(Objects.isNull(dish)){
			throw new ApplicationException(ErrorCodes.Dish.EMPTY_REQUEST, NOT_PRESENT);
		}
		dish.setCategory(null);
		List<Ingredient> ingredients = dish.getIngredients();
		dish.getIngredients().removeAll(ingredients);
		for(Ingredient ingredient : ingredients) {
			ingredient.getDish().remove(dish);
		}
		this.dao.remove(dish);
	}

	@Override
	@Transactional
	public Dish get(Long id) throws BaseException {
		Dish dish = this.dao.get(id);
		if(Objects.isNull(dish)){
			throw new ApplicationException(ErrorCodes.Dish.EMPTY_REQUEST, ID_NOT_PRESENT);
		}
		return dish;
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
			throw new ApplicationException(ErrorCodes.Dish.EMPTY_REQUEST, EMPTY_REQUEST);
		}
		Dish dish;
		switch (operation) {
		case CREATE:
			dish = new Dish();
			if(!validate(request.getName())){
				throw new ApplicationException(ErrorCodes.Dish.INVALID_REQUEST, ALREADY_EXISTS);
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
				Category category = categoryRepository.findById(request.getCategoryId());
				nullPointerService.isNull(category);
				dish.setCategory(category);
				category.getDishes().add(dish);
			}
			dish.setEnabled(Boolean.TRUE);
			dao.add(dish);
			return dish;
		case UPDATE:
			dish = repository.findById(request.getId());
			nullPointerService.isNull(dish);
			if(!validate(request.getName())){
				throw new ApplicationException(ErrorCodes.Dish.INVALID_REQUEST, ALREADY_EXISTS);
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
				Category category = categoryRepository.findById(request.getCategoryId());
				nullPointerService.isNull(category);
				category.getDishes().remove(dish);
				dish.setCategory(category);
				category.getDishes().add(dish);
			}
			dao.update(dish);
			return dish;
		default:
			throw new ApplicationException(ErrorCodes.Common.INVALID_REQUEST, INVALID_REQUEST);
		}
	}
	
	public Dish addIngredients(DishModel request) throws BaseException {
		if(Objects.isNull(request)){
			throw new ApplicationException(ErrorCodes.Dish.EMPTY_REQUEST, EMPTY_REQUEST);
		}
		if(Objects.isNull(request.getId())){
			throw new ApplicationException(ErrorCodes.Dish.EMPTY_FIELD, ID_NOT_PRESENT);
		}
		if(Objects.isNull(request.getIngredients())){
			throw new ApplicationException(ErrorCodes.Ingredient.EMPTY_REQUEST, EMPTY_REQUEST);
		}
		Dish dish = repository.findById(request.getId());
		if(Objects.isNull(dish)){
			throw new ApplicationException(ErrorCodes.Dish.EMPTY_REQUEST, "This dish are not present or blocked");
		}
		List<Ingredient> ingredients = new ArrayList<>();
		for(IngredientModel ingredient: request.getIngredients()){
			Ingredient ingred = ingredientRepository.findById(ingredient.getId());
			if(ingred!=null)
				ingredients.add(ingred);
		}
		dish.getIngredients().addAll(ingredients);
		return dish;
	}
	
	public Dish removeIngredients(DishModel request) throws BaseException {
		if(Objects.isNull(request)) {
			throw new ApplicationException(ErrorCodes.Dish.EMPTY_REQUEST, "Requets are empty");
		}
		if(Objects.isNull(request.getId())){
			throw new ApplicationException(ErrorCodes.Dish.EMPTY_FIELD, "Field ID are empty");
		}
		if(Objects.isNull(request.getIngredients())){
			throw new ApplicationException(ErrorCodes.Dish.EMPTY_REQUEST, "List of ingredients are empty");
		}
		Dish dish = repository.findById(request.getId());
		if(Objects.isNull(dish)){
			throw new ApplicationException(ErrorCodes.Dish.INVALID_REQUEST, "This dish is not present or blocked");
		}
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		for(IngredientModel model : request.getIngredients()){
			Ingredient ingredient = ingredientRepository.findById(model.getId());
			if(ingredient!=null)
				ingredients.add(ingredient);
			
		}
		dish.getIngredients().removeAll(ingredients);
		return dish;
	}
	
	public Dish activation(Long id, CrudOperation operation) throws BaseException {
		if(Objects.isNull(id)){
			throw new ApplicationException(ErrorCodes.Dish.EMPTY_REQUEST, "ID is not present");
		}
		Dish dish;
		switch (operation) {
		case BLOCK:
			dish = repository.findById(id);
			if(Objects.isNull(dish)){
				throw new ApplicationException(ErrorCodes.Dish.EMPTY_REQUEST, "This dish are not present or blocked");
			}
			dish.setEnabled(Boolean.FALSE);
			return dish;
		case UNBLOCK:
			dish = get(id);
			if(Objects.isNull(dish)){
				throw new ApplicationException(ErrorCodes.Dish.EMPTY_REQUEST, "This dish are not present");
			}
			dish.setEnabled(Boolean.TRUE);
			return dish;
		default:
			throw new ApplicationException(ErrorCodes.Dish.INVALID_REQUEST, "Invalid request");
		}
	}
	
	protected boolean validateName(String name) throws BaseException {
		Pattern patter = Pattern.compile("^([A-Z]{1}[a-z]{1,15}[\\s]{0,1})+$");
		Matcher matcher = patter.matcher(name);
		if(!matcher.matches()){
			throw new ApplicationException(ErrorCodes.Dish.INVALID_REQUEST, INVALID_DISH_NAME);
		}
		return matcher.matches();
	}
	
	protected boolean validatePrice(Double price) throws BaseException {
		Pattern pattern = Pattern.compile("^([0-9]{2,4}[\\.,]{0,1}[0-9]{0,4})+$");
		Matcher matcher = pattern.matcher(price.toString());
		if(!matcher.matches()){
			throw new ApplicationException(ErrorCodes.Dish.INVALID_REQUEST, INVALID_DISH_PRICE);
		}
		return matcher.matches();
	}
	
	protected boolean validateWeight(Double weight) throws BaseException {
		Pattern pattern = Pattern.compile("^([0-9]{2,4}[\\.,]{0,1}[0-9]{0,4})+$");
		Matcher matcher = pattern.matcher(weight.toString());
		if(!matcher.matches()){
			throw new ApplicationException(ErrorCodes.Dish.INVALID_REQUEST, INVALID_DISH_WEIGHT);
		}
		return matcher.matches();
	}

	@Override
	public Dish addImages(DishModel request) throws BaseException {
		if (Objects.isNull(request)) {
			throw new ApplicationException(ErrorCodes.Dish.EMPTY_REQUEST,
					"Empty Request");
		}
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
		update(dish);
		return dish;
	}

	@Override
	public Dish removeImages(DishModel request) throws BaseException {
		// TODO Auto-generated method stub
		return null;
	}
}
