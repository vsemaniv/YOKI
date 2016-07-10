package com.cusbee.yoki.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cusbee.yoki.entity.Ingredient;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long>{

	Ingredient findByName(String name);
		
		
}
