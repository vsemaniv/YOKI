package com.cusbee.yoki.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cusbee.yoki.entity.Ingredient;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long>{

	Ingredient findByName(String name);
		
<<<<<<< HEAD
	@Query(value="SELECT i.* FROM ingredient i WHERE i.id=?1 AND enabled='Y'", nativeQuery=true)
=======
	@Query(value="SELECT i.* FROM Ingredient i WHERE i.id=?1 AND enabled='Y'", nativeQuery=true)
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
	Ingredient findById(Long id);
		
}
