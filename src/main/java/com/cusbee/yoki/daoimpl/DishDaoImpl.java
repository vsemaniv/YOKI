package com.cusbee.yoki.daoimpl;

import java.util.List;

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

	@Override
	public void update(Dish dish) {
		em.merge(dish);
	}

	@Override
	public void remove(Dish dish) {
		em.remove(dish);
	}

	@Override
	public Dish getById(Long id) {
		return em.find(Dish.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Dish> getAll() {
		return (List<Dish>) em.createQuery("SELET d FROM Dish d").getResultList();
	}

}
