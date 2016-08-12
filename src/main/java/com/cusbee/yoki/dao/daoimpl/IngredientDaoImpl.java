package com.cusbee.yoki.dao.daoimpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cusbee.yoki.dao.IngredientDao;
import com.cusbee.yoki.entity.Ingredient;

@Repository
@Transactional
public class IngredientDaoImpl implements IngredientDao {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Ingredient save(Ingredient ingredient) {
		em.merge(ingredient);
		return ingredient;
	}

	@Override
	@Transactional
	public Ingredient get(Long id) {
		return em.find(Ingredient.class, id);
	}

	@Override
	public void remove(Ingredient ingredient) {
		em.remove(ingredient);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Ingredient> getAll() {
		return (List<Ingredient>) em.createQuery("SELECT i FROM Ingredient i").getResultList();
	}

}
