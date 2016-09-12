package com.cusbee.yoki.dao;

import com.cusbee.yoki.entity.Ingredient;

import java.util.List;

/**
 * Created on 12.09.2016.
 */
public interface IngredientDao {

    Ingredient save(Ingredient ingredient);

    void remove(Ingredient ingredient);

    Ingredient get(Long id);

    List<Ingredient> getAll();
}
