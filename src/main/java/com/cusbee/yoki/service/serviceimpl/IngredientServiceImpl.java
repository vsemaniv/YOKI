package com.cusbee.yoki.service.serviceimpl;

import java.util.List;

import com.cusbee.yoki.service.ActivationService;
import com.cusbee.yoki.service.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cusbee.yoki.dao.IngredientDao;
import com.cusbee.yoki.entity.CrudOperation;
import com.cusbee.yoki.entity.Dish;
import com.cusbee.yoki.entity.Ingredient;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.model.IngredientModel;
import com.cusbee.yoki.repositories.IngredientRepository;
import com.cusbee.yoki.service.IngredientService;
import com.cusbee.yoki.utils.ErrorCodes;

@Service
@Transactional
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private IngredientDao dao;

    @Autowired
    private IngredientRepository repository;

    @Autowired
    private ValidatorService validatorService;

    @Autowired
    private ActivationService activationService;

    @Override
    @Transactional
    public Ingredient get(Long id) {
        validatorService.validateRequestIdNotNull(id);
        Ingredient ingredient = dao.get(id);
        validatorService.validateEntityNotNull(ingredient, Ingredient.class);
        return ingredient;
    }

    @Transactional
    public void remove(Long id) {
        Ingredient ingredient = get(id);
        List<Dish> dishes = ingredient.getDishes();
        if (dishes.isEmpty()) {
            dao.remove(ingredient);
        } else {
            StringBuilder message = new StringBuilder("This ingredient is used in at least one dish. Please, remove it from the next dishes: ");
            boolean flag = false;
            for (Dish dish : dishes) {
                if(flag) {
                    message.append(", ");
                }
                message.append(dish.getName());
                flag = true;
            }
            throw new ApplicationException(ErrorCodes.Ingredient.STILL_USED, message.toString());
        }
    }

    @Override
    public List<Ingredient> getAll() {
        return dao.getAll();
    }

    @Override
    @Transactional
    public Ingredient saveIngredient(IngredientModel request, CrudOperation status) {
        validatorService.validateIngredientSaveRequest(request, status);
        Ingredient ingredient;
        switch (status) {
            case CREATE:
                ingredient = new Ingredient();

                break;
            case UPDATE:
                ingredient = repository.findById(request.getId());
                validatorService.validateEntityNotNull(ingredient, Ingredient.class);
                //TODO i think we would not assign any dish to ingredient, therefore we should not touch dishes while updating ingredient
                /*
                List<Dish> dishes = ingredient.getDishes();
                if (CollectionUtils.isEmpty(dishes)) {
                    ingredient.setDishes(new ArrayList<Dish>());
                } else {
                    ingredient.getDishes().addAll(dishes);
                }*/
                break;
            case BLOCK:


            default:
                throw new ApplicationException(ErrorCodes.Ingredient.EMPTY_REQUEST,
                        "Server resolve your request");
        }
        ingredient.setName(request.getName());
        ingredient.setValue(request.getValue());
        ingredient.setDescription(request.getDescription());
        ingredient.setType(request.getType());
        return dao.save(ingredient);
    }

    public Ingredient processActivation(Long id, boolean activate) {
        Ingredient ingredient = get(id);
        activationService.processActivation(ingredient, activate);
        return dao.save(ingredient);
    }
}
