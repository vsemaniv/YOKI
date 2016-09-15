package com.cusbee.yoki.dao.daoimpl;

import com.cusbee.yoki.dao.CourierDao;

import com.cusbee.yoki.entity.CourierDetails;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.List;

@Repository
@Transactional
public class CourierDaoImpl implements CourierDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public CourierDetails save(CourierDetails courierDetails) {
        return em.merge(courierDetails);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<CourierDetails> getAllCouriers(){
        return (List<CourierDetails>) em.createQuery("SELECT cd FROM CourierDetails cd").getResultList();
    }

    @Override
    public CourierDetails get(Long id) {
        return em.find(CourierDetails.class, id);
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<CourierDetails> getAvailableCouriers() {
		return (List<CourierDetails>) em.createQuery("SELECT cd FROM CourierDetails cd WHERE cd.status='FREE'").getResultList();
	}
}
