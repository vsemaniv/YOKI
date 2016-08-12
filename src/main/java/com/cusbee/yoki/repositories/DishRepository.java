package com.cusbee.yoki.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cusbee.yoki.entity.Dish;

public interface DishRepository extends JpaRepository<Dish, Long> {

	Dish findByName(String name);
	
<<<<<<< HEAD
<<<<<<< HEAD
	@Query(value="SELECT d.* FROM dish d WHERE d.id = (:id) AND enabled='Y'", nativeQuery=true)
	Dish findById(@Param("id") Long id);
	
	@Query(value="SELECT d.* FROM dish d WHERE enabled='Y' AND dish_type IS NOT NULL", nativeQuery=true)
=======
	@Query(value="SELECT d.* FROM Dish d WHERE d.id = (:id) AND enabled='Y'", nativeQuery=true)
	Dish findById(@Param("id") Long id);
	
	@Query(value="SELECT d.* FROM Dish d WHERE enabled='Y'", nativeQuery=true)
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
=======
	@Query(value="SELECT d.* FROM dish d WHERE d.id = (:id) AND enabled='Y'", nativeQuery=true)
	Dish findById(@Param("id") Long id);
	
	@Query(value="SELECT d.* FROM dish d WHERE enabled='Y' AND dish_type IS NOT NULL", nativeQuery=true)
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
	List<Dish> findAll();
}
