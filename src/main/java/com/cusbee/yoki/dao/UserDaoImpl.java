package com.cusbee.yoki.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cusbee.yoki.entity.User;

@Repository("userDaoImpl")
@Transactional
public class UserDaoImpl implements UserDao {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void add(User user) {
		em.merge(user);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<User> getAll(){
		return (List<User>) em.createQuery("SELECT u FROM User u").getResultList();
	}

	@Override
	public User getById(Long id) {
		return em.find(User.class, id);
	}

}
