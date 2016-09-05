package com.cusbee.yoki.dao.daoimpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cusbee.yoki.dao.AccountDao;
import com.cusbee.yoki.entity.Account;

@Repository("userDaoImpl")
@Transactional
public class AccountDaoImpl implements AccountDao {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Account save(Account user) {
		return em.merge(user);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Account> getAll(){
		return (List<Account>) em.createQuery("SELECT a FROM Account a").getResultList();
	}

	@Override
	public Account get(Long id) {
		return em.find(Account.class, id);
	}

}
