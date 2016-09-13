package com.cusbee.yoki.service;

import com.cusbee.yoki.entity.Ingredient;
import java.util.List;

/**
 * Created on 12.09.2016.
 */

public interface IngredientService {

    List<Ingredient> getAll();

    Ingredient get(Long id);

    Ingredient addIngredient(Ingredient request);

    Ingredient updateIngredient(Ingredient request);

    void remove(Long id);
}
