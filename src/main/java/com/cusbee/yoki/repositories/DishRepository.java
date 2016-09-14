package com.cusbee.yoki.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cusbee.yoki.entity.Dish;
import org.springframework.data.jpa.repository.Query;

public interface DishRepository extends JpaRepository<Dish, Long> {

	@Query(value="SELECT d.* FROM dish d WHERE d.name=?1", nativeQuery=true)
	Dish findByName(String name);

}
