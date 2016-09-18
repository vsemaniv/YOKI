package com.cusbee.yoki.service.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import com.cusbee.yoki.entity.*;
import com.cusbee.yoki.entity.enums.CrudOperation;
import com.cusbee.yoki.entity.enums.DishType;
import com.cusbee.yoki.repositories.IngredientRepository;
import com.cusbee.yoki.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cusbee.yoki.dao.DishDao;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.model.DishModel;

import javax.transaction.Transactional;

//TODO make separate cache for get alls
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

    @Autowired
    private IngredientRepository ingredientRepository;

    @Override
    @CacheEvict(cacheNames = {"dish", "all_dishes", "av_dishes", "category_av_dishes", "category_all_dishes"}, allEntries = true)
    public Dish save(Dish dish) {
        return dao.save(dish);
    }

    @Override
    @Cacheable("dish")
    public Dish get(Long id) {
        validatorService.validateRequestIdNotNull(id, Dish.class);
        Dish dish = dao.get(id);
        validatorService.validateEntityNotNull(dish, Dish.class);
        return dish;
    }

    @Override
    @Cacheable("all_dishes")
    public List<Dish> getAll() {
        return dao.getAll();
    }

    @Override
    @Cacheable("av_dishes")
    public List<Dish> getAvailable() {
        return dao.getAvailable();
    }

    @Override
    @CacheEvict(cacheNames = {"dish", "all_dishes", "av_dishes", "category_av_dishes", "category_all_dishes"}, allEntries = true)
    public void remove(Long id) {
        Dish dish = get(id);
        dish.setCategory(null);
        removeImages(dish.getImages());
        dao.remove(dish);
    }


    @Override
    @CacheEvict(cacheNames = {"dish", "all_dishes", "av_dishes", "category_av_dishes", "category_all_dishes"}, allEntries = true)
    public Dish saveDish(DishModel request, CrudOperation operation) {
        Dish dish;
        validatorService.validateDishSaveRequest(request, operation);
        switch (operation) {
            case CREATE:
                dish = new Dish();
                dish.setImages(new ArrayList<DishImage>());
                dish.setIngredients(new ArrayList<Ingredient>());
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
        if(CollectionUtils.isNotEmpty(request.getIngredientIds())) {
            remapDishIngredients(dish, request.getIngredientIds());
        }
        return dao.save(dish);
    }

    @Override
    @CacheEvict(cacheNames = {"dish", "all_dishes", "av_dishes", "category_av_dishes", "category_all_dishes"}, allEntries = true)
    public Dish processActivation(Long id, boolean activate) {
        Dish dish = get(id);
        activationService.processActivation(dish, activate);
        return dao.save(dish);
    }

    @Override
    public List<Ingredient> getIngredients(Long dishId) {
        return ingredientRepository.getDishIngredients(dishId);
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

    private void remapDishIngredients(Dish dish, List<Long> ingredientIds) {
        List<Ingredient> ingredientList = dish.getIngredients();
        ingredientList.clear();
        for(Long ingredientId : ingredientIds) {
            ingredientList.add(ingredientService.get(ingredientId));
        }
        dish.setIngredients(ingredientList);
    }
}
