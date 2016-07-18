package com.cusbee.yoki.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cusbee.yoki.entity.Ingredient;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long>{

	Ingredient findByName(String name);
		
	@Query(value="SELECT i.* FROM Ingredient i WHERE i.id=?1", nativeQuery=true)
	Ingredient findById(Long id);
		
}
