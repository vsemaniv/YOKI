package com.cusbee.yoki.service.serviceimpl;

import com.cusbee.yoki.dao.IngredientDao;
import com.cusbee.yoki.entity.Dish;
import com.cusbee.yoki.entity.Ingredient;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.model.IngredientModel;
import com.cusbee.yoki.service.ImageService;
import com.cusbee.yoki.service.IngredientService;
import com.cusbee.yoki.service.ValidatorService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 12.09.2016.
 */
@Service
@Transactional
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private IngredientDao dao;

    @Autowired
    private ValidatorService validatorService;

    @Autowired
    private ImageService imageService;

    @Override
    @CacheEvict(cacheNames = {"ingredient", "ingredients"}, allEntries = true)
    public Ingredient save(Ingredient ingredient) {
        return dao.save(ingredient);
    }

    @Override
    @Cacheable("ingredients")
    public List<Ingredient> getAll() {
        return dao.getAll();
    }

    @Override
    @Cacheable("ingredient")
    public Ingredient get(Long id) {
        validatorService.validateRequestIdNotNull(id, Ingredient.class);
        Ingredient ingredient = dao.get(id);
        validatorService.validateEntityNotNull(ingredient, Ingredient.class);
        return ingredient;
    }

    @Override
    @CacheEvict(cacheNames = {"ingredient", "ingredients"}, allEntries = true)
    public Ingredient addIngredient(IngredientModel request) {
        Ingredient ingredient = new Ingredient();
        if(StringUtils.isNotEmpty(request.getName())) {
            ingredient.setName(request.getName());
        }
        return dao.save(ingredient);
    }

    @Override
    @CacheEvict(cacheNames = {"ingredient", "ingredients"}, allEntries = true)
    public Ingredient updateIngredient(IngredientModel request) {
        Ingredient ingredient = get(request.getId());
        if(StringUtils.isNotEmpty(request.getName())) {
            ingredient.setName(request.getName());
        }
        return dao.save(ingredient);
    }

    @Override
    @CacheEvict(cacheNames = {"ingredient", "ingredients"}, allEntries = true)
    public void remove(Long id) {
        Ingredient ingredient = get(id);
        List<Dish> dishesWithIngredient = new ArrayList<>(ingredient.getDishes());
        if(CollectionUtils.isEmpty(dishesWithIngredient)) {
            if(StringUtils.isNotEmpty(ingredient.getIconLink())) {
                imageService.removeIngredientIcon(ingredient.getIconLink());
            }
            dao.remove(ingredient);
        } else {
            StringBuilder sb = new StringBuilder("Unable to remove ingredient. It is still present in the next dishes: ");
            for(Dish dish : dishesWithIngredient) {
                sb.append(dish.getName() + ",");
            }
            sb.deleteCharAt(sb.length()-1);
            throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR, sb.toString());
        }

    }
}
