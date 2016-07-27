package com.cusbee.yoki.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cusbee.yoki.entity.Dish;

public interface DishRepository extends JpaRepository<Dish, Long> {

	Dish findByName(String name);
	
	@Query(value="SELECT d.* FROM dish d WHERE d.id = (:id) AND enabled='Y'", nativeQuery=true)
	Dish findById(@Param("id") Long id);
	
	@Query(value="SELECT d.* FROM dish d WHERE enabled='Y' AND dish_type IS NOT NULL", nativeQuery=true)
	List<Dish> findAll();
}
