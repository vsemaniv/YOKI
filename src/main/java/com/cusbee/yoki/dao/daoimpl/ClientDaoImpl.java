package com.cusbee.yoki.dao.daoimpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cusbee.yoki.dao.ClientDao;
import com.cusbee.yoki.entity.Client;
import com.cusbee.yoki.exception.BaseException;

@Repository
@Transactional 
public class ClientDaoImpl implements ClientDao{

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void add(Client client) {
		em.merge(client);
	}

	@Override
	public void update(Client client) {
		em.merge(client);
	}

	@Override
	public Client get(Long id) throws BaseException {
		return em.find(Client.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Client> getAll() {
		return (List<Client>) em.createQuery("SELECT c FROM Client c").getResultList();
	}

}
