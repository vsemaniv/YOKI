package com.cusbee.yoki.dao.daoimpl;

import com.cusbee.yoki.dao.IngredientDao;
import com.cusbee.yoki.entity.Ingredient;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created on 12.09.2016.
 */
@Repository
public class IngredientDaoImpl implements IngredientDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Ingredient save(Ingredient ingredient) {
        return em.merge(ingredient);
    }

    @Override
    public void remove(Ingredient ingredient) {
        em.remove(ingredient);
    }

    @Override
    @Transactional
    public Ingredient get(Long id) {
        return em.find(Ingredient.class, id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Ingredient> getAll() {
        return (List<Ingredient>) em.createQuery("SELECT i FROM Ingredient i").getResultList();
    }
}
