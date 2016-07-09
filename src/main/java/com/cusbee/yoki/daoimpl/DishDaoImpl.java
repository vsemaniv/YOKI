package com.cusbee.yoki.daoimpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cusbee.yoki.dao.DishDao;
import com.cusbee.yoki.entity.Dish;

@Repository("dishImpl")
@Transactional
public class DishDaoImpl implements DishDao {

	@PersistenceContext
	private EntityManager em;
	
	public void add(Dish dish) {
		em.merge(dish);
	}

}
