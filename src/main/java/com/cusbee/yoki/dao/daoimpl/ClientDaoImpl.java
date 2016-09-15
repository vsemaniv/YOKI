package com.cusbee.yoki.dao.daoimpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.cusbee.yoki.dao.ClientDao;
import com.cusbee.yoki.entity.Client;

@Repository
@Transactional
public class ClientDaoImpl implements ClientDao{

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Client save(Client client) {
		return em.merge(client);
	}

	@Override
	public Client get(String phone) {
		return em.find(Client.class, phone);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Client> getAll() {
		return (List<Client>) em.createQuery("SELECT c FROM Client c").getResultList();
	}
}
