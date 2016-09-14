package com.cusbee.yoki.repositories;



import com.cusbee.yoki.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cusbee.yoki.entity.Dish;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Long> {

	@Query(value="SELECT d.* FROM dish d WHERE d.name=?1", nativeQuery=true)
	Dish findByName(String name);

	@Query(value="SELECT i.* FROM dish d, dish_ingredient di, ingredient i " +
			"WHERE d.id=?1 AND d.id = di.dish_id AND di.ingredients_id = i.id", nativeQuery=true)
	List<Ingredient> getDishIngredients(Long dishId);
}
