package com.cusbee.yoki.service.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.cusbee.yoki.entity.*;
import com.cusbee.yoki.model.ImageModel;
import com.cusbee.yoki.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cusbee.yoki.dao.DishDao;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.model.DishModel;
import com.cusbee.yoki.utils.ErrorCodes;

@Service
@Transactional
public class DishServiceImpl implements DishService {

    @Autowired
    private DishDao dao;

    @Autowired
    private ImageService imageService;

    @Autowired
    private CategoryService categoryService;

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
        for (Ingredient ingredient : ingredients) {
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
                /*if (!Objects.isNull(request.getCategoryId())) {
                    Category category = categoryRepository.findById(request.getCategoryId());
                    nullPointerService.isNull(category);
                    dish.setCategory(category);
                    category.getDishes().add(dish);
                }*/
                dish.setEnabled(Boolean.TRUE);
                break;
            case UPDATE:
                dish = dao.get(request.getId());
                break;
            default:
                throw new ApplicationException(ErrorCodes.Common.INVALID_REQUEST, "Invalid request");
        }
        dish.setName(request.getName());
        dish.setPrice(request.getPrice());
        dish.setWeight(request.getWeight());
        dish.setDescription(request.getDescription());
        dish.setType(getDishType(request));
        Long categoryId = request.getCategoryId();
        dish.setCategory(categoryId == null ? null : categoryService.get(categoryId));
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
        validatorService.validateRequestIdNotNull(request.getId());
        if (Objects.isNull(request.getImages())) {
            throw new ApplicationException(ErrorCodes.Dish.EMPTY_FIELD,
                    "You don't input no one dish to adding");
        }
        Dish dish = dao.get(request.getId());
        validatorService.validateEntityNotNull(dish, Dish.class);
        List<DishImage> images = new ArrayList<>();
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

    private DishType getDishType(DishModel request) {
        String type = request.getType();
        return validatorService.isEnumValid(type, DishType.class) ?
                DishType.valueOf(type.toUpperCase()) : DishType.ORDINARY;
    }
}
