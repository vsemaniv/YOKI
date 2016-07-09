package com.cusbee.yoki.daoimpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cusbee.yoki.dao.CategoryDao;
import com.cusbee.yoki.entity.Category;

@Repository
@Transactional
public class CategoryDaoImpl implements CategoryDao {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void add(Category category) {
		em.merge(category);
	}

	@Override
	public void update(Category category) {
		em.merge(category);
	}

	@Override
	public Category getById(Long id) {
		return em.find(Category.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getAll() {
		// TODO Auto-generated method stub	
		return (List<Category>) em.createQuery("SELECT c FROM Category c").getResultList();
	}

	@Override
	public void remove(Category category) {
		em.remove(em.contains(category) ? category : em.merge(category));
	}

}
