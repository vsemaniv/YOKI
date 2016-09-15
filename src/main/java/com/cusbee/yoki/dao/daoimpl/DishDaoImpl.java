package com.cusbee.yoki.dao.daoimpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.cusbee.yoki.entity.*;
import org.springframework.stereotype.Repository;

import com.cusbee.yoki.dao.DishDao;

@Repository("dishImpl")
@Transactional
public class DishDaoImpl implements DishDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Dish save(Dish dish) {
		return em.merge(dish);
	}

	@Override
	public void remove(Dish dish) {
		em.remove(dish);
	}

	@Override
	@Transactional
	public Dish get(Long id) {
		return em.find(Dish.class, id);
	}

	@Override
	public DishImage getDishImageByLink(String link) {
		return (DishImage) em.createQuery("SELECT di FROM DishImage di WHERE di.link = ?1")
				.setParameter(1, link).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Dish> getAll() {
		return (List<Dish>) em.createQuery("SELECT d FROM Dish d").getResultList();
	}

	@Override
	public List<Dish> getAvailable() {
		return (List<Dish>) em.createQuery("SELECT d FROM Dish d WHERE d.enabled = true").getResultList();
	}

	@Override
	public List<Dish> getAvailable(Category category) {
		return (List<Dish>) em.createQuery("SELECT d FROM Dish d WHERE d.enabled = 'Y' AND d.category = ?1").setParameter(1, category).getResultList();
	}

	@Override
	public List<DishQuantity> getDishesByOrder(Order order) {
		return (List<DishQuantity>) em.createQuery("SELECT dq FROM DishQuantity dq WHERE dq.order = ?1")
				.setParameter(1, order).getResultList();
	}
}
