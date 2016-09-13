package com.cusbee.yoki.service.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import com.cusbee.yoki.entity.*;
import com.cusbee.yoki.entity.enums.CrudOperation;
import com.cusbee.yoki.entity.enums.DishType;
import com.cusbee.yoki.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cusbee.yoki.dao.DishDao;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.model.DishModel;

@Service
@Transactional
public class DishServiceImpl implements DishService {

    @Autowired
    private DishDao dao;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private ValidatorService validatorService;

    @Autowired
    private ActivationService activationService;

    @Autowired
    private ImageService imageService;

    @Override
    @Transactional
    public Dish get(Long id) {
        validatorService.validateRequestIdNotNull(id, Dish.class);
        Dish dish = dao.get(id);
        validatorService.validateEntityNotNull(dish, Dish.class);
        return dish;
    }

    @Override
    public List<Dish> getAll() {
        return dao.getAll();
    }

    public List<Dish> getAvailable() {
        return dao.getAvailable();
    }

    @Override
    @Transactional
    public void remove(Long id) {
        Dish dish = get(id);
        dish.setCategory(null);
        removeImages(dish.getImages());
        dao.remove(dish);
    }


    @Override
    @Transactional
    public Dish saveDish(DishModel request, CrudOperation operation) {
        Dish dish;
        validatorService.validateDishSaveRequest(request, operation);
        switch (operation) {
            case CREATE:
                dish = new Dish();
                dish.setImages(new ArrayList<DishImage>());
                dish.setEnabled(Boolean.TRUE);
                break;
            case UPDATE:
                dish = dao.get(request.getId());
                break;
            default:
                throw new ApplicationException(HttpStatus.BAD_REQUEST, "Invalid request");
        }
        if(request.getName() != null) {
            dish.setName(request.getName());
        }
        if(request.getPrice() != null) {
            dish.setPrice(request.getPrice());
        }
        if(request.getDescription() != null) {
            dish.setDescription(request.getDescription());
        }
        if(request.getType() != null) {
            dish.setType(getDishType(request));
        }
        Long categoryId = request.getCategoryId();
        dish.setCategory(categoryId == null ? null : categoryService.get(categoryId));
        if(CollectionUtils.isNotEmpty(request.getIngredients())) {
            addIngredientsToDish(dish, request.getIngredients());
        }
        return dao.save(dish);
    }

    @Override
    public Dish processActivation(Long id, boolean activate) {
        Dish dish = get(id);
        activationService.processActivation(dish, activate);
        return dao.save(dish);
    }

    private DishType getDishType(DishModel request) {
        String type = request.getType();
        return validatorService.isEnumValid(type, DishType.class) ?
                DishType.valueOf(type.toUpperCase()) : DishType.ORDINARY;
    }

    private void removeImages(List<DishImage> dishImages) {
        List<String> links = new ArrayList<>();
        for(DishImage dishImage : dishImages) {
            links.add(dishImage.getLink());
        }
        imageService.removeDishImages(links);
    }

    private void addIngredientsToDish(Dish dish, List<Ingredient> ingredients) {
        List<Ingredient> ingredientList = dish.getIngredients();
        for(Ingredient ingredientRequest : ingredients) {
            ingredientList.add(ingredientService.get(ingredientRequest.getId()));
        }
    }
}
