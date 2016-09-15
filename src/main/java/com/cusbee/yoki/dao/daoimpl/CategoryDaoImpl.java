package com.cusbee.yoki.dao.daoimpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.cusbee.yoki.dao.CategoryDao;
import com.cusbee.yoki.entity.Category;

@Repository
@Transactional
public class CategoryDaoImpl implements CategoryDao {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Category save(Category category) {
		return em.merge(category);
	}

	@Override
	public Category get(Long id) {
		return em.find(Category.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getAll() {
		// TODO Auto-generated method stub	
		return (List<Category>) em.createQuery("SELECT c FROM Category c WHERE c.enabled=true").getResultList();
	}

	@Override
	public void remove(Category category) {
		em.remove(em.contains(category) ? category : em.merge(category));
	}

}
