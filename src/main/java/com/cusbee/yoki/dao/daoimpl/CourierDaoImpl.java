package com.cusbee.yoki.dao.daoimpl;

import com.cusbee.yoki.dao.CourierDao;

import com.cusbee.yoki.entity.CourierDetails;
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
    public CourierDetails save(CourierDetails courierDetails) {
        em.merge(courierDetails);
        return courierDetails;
    }

    @Override
    @SuppressWarnings("unchecked")
    @Transactional
    public List<CourierDetails> getAllCouriers(){
        return (List<CourierDetails>) em.createQuery("SELECT a FROM Courier a").getResultList();
    }

    @Override
    public CourierDetails get(Long id) {
        return em.find(CourierDetails.class, id);
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<CourierDetails> getAvailableCouriers() {
		return (List<CourierDetails>) em.createQuery("SELECT c FROM CourierDetails c WHERE c.status='FREE'").getResultList();
	}
}
