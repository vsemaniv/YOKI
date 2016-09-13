package com.cusbee.yoki.service.serviceimpl;

import com.cusbee.yoki.dao.IngredientDao;
import com.cusbee.yoki.entity.Ingredient;
import com.cusbee.yoki.service.IngredientService;
import com.cusbee.yoki.service.ValidatorService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created on 12.09.2016.
 */
@Service
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private IngredientDao dao;

    @Autowired
    private ValidatorService validatorService;

    @Override
    public List<Ingredient> getAll() {
        return dao.getAll();
    }

    @Override
    public Ingredient get(Long id) {
        validatorService.validateRequestIdNotNull(id, Ingredient.class);
        Ingredient ingredient = dao.get(id);
        validatorService.validateEntityNotNull(ingredient, Ingredient.class);
        return ingredient;
    }

    @Override
    public Ingredient addIngredient(Ingredient request) {
        Ingredient ingredient = new Ingredient();
        if(StringUtils.isNotEmpty(request.getName())) {
            ingredient.setName(request.getName());
        }
        return dao.save(ingredient);
    }

    @Override
    public Ingredient updateIngredient(Ingredient request) {
        Ingredient ingredient = get(request.getId());
        if(StringUtils.isNotEmpty(request.getName())) {
            ingredient.setName(request.getName());
        }
        return dao.save(ingredient);
    }

    @Override
    public void remove(Long id) {
        Ingredient ingredient = get(id);
        dao.remove(ingredient);
    }
}