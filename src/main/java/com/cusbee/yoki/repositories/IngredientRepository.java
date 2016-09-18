package com.cusbee.yoki.repositories;

import com.cusbee.yoki.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    @Query(value="SELECT i.* FROM dish d, dish_ingredient di, ingredient i " +
            "WHERE d.id=?1 AND d.id = di.dish_id AND di.ingredient_id = i.id", nativeQuery=true)
    List<Ingredient> getDishIngredients(Long dishId);
}
