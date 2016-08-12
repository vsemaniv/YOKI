package com.cusbee.yoki.service;

import com.cusbee.yoki.entity.BaseEntity;
import com.cusbee.yoki.entity.enums.CrudOperation;
import com.cusbee.yoki.model.*;

/**

 */
public interface ValidatorService {

    void validateAccountSaveRequest(AccountModel request, CrudOperation operation);

    void validateCategorySaveRequest(CategoryModel request, CrudOperation operation);

<<<<<<< HEAD
    void validateIngredientSaveRequest(IngredientModel request, CrudOperation operation);

=======
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
    void validateDishSaveRequest(DishModel request, CrudOperation operation);

    void validateOrderSaveRequest(OrderModel request, CrudOperation operation);

    void validateRequestNotNull(RequestModel request, Class entityClass);

    void validateRequestIdNotNull(Long id);

    void validateEntityNotNull(BaseEntity entity, Class entityClass);

    <E extends Enum<E>> boolean isEnumValid(String type, Class<E> enumClass);
}
