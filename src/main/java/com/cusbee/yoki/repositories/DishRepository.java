package com.cusbee.yoki.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.cusbee.yoki.entity.Dish;

public interface DishRepository extends JpaRepository<Dish, Long> {

	Dish findByName(String name);
	
}
