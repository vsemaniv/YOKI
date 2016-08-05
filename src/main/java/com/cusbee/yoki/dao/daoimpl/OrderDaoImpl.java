package com.cusbee.yoki.dao.daoimpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cusbee.yoki.dao.OrderDao;
import com.cusbee.yoki.entity.Order;

@Repository
@Transactional
public class OrderDaoImpl implements OrderDao {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Order save(Order order) {
		em.merge(order);
		return order;
	}

	@Override
	public void remove(Order order) {
		em.remove(order);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getAll() {
		return (List<Order>) em.createQuery("SELECT o FROM Order o").getResultList();
	}

	public List<Order> getAvailableOrders() {
		return (List<Order>) em.createQuery("SELECT o FROM Order o WHERE o.status NOT IN('CLOSED', 'DONE')");
	}

	@Override
	public Order get(Long id) {
		return em.find(Order.class, id);
	}

}
