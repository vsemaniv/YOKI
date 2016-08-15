package com.cusbee.yoki.dao.daoimpl;

import com.cusbee.yoki.dao.CourierDao;
import com.cusbee.yoki.entity.Courier;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

@Repository
@Transactional
public class CourierDaoImpl implements CourierDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Courier save(Courier courier) {
        em.merge(courier);
        return courier;
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional
    public List<Courier> getAll(){
        return (List<Courier>) em.createQuery("SELECT a FROM Courier a").getResultList();
    }

    @Override
    public Courier get(Long id) {
        return em.find(Courier.class, id);
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Courier> getAllAvailableCouriers() {
		return (List<Courier>) em.createQuery("SELECT c FROM Courier c WHERE c.status='FREE'").getResultList();
	}
}
