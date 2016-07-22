package com.cusbee.yoki.daoimpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cusbee.yoki.dao.ImageDao;
import com.cusbee.yoki.entity.DishImage;

@Repository
@Transactional
public class ImageDaoImpl implements ImageDao{
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public void add(DishImage dishImage) {
		em.merge(dishImage);
		
	}

	@Override
	public void update(DishImage dishImage) {
		em.merge(dishImage);
		
	}

	@Override
	@Transactional
	public DishImage get(Long id) {
		return em.find(DishImage.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DishImage> getAll() {
		return (List<DishImage>) em.createQuery("SELEÑT d FROM DishImage d").getResultList();
	}

	@Override
	public void remove(DishImage dishImage) {
		em.remove(dishImage);
	}
	
	

}