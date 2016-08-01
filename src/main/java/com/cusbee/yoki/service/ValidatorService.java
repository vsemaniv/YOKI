package com.cusbee.yoki.service;

import com.cusbee.yoki.entity.BaseEntity;
import com.cusbee.yoki.entity.CrudOperation;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.AccountModel;
import com.cusbee.yoki.model.CategoryModel;
import com.cusbee.yoki.model.IngredientModel;
import com.cusbee.yoki.model.RequestModel;

/**

 */
public interface ValidatorService {

    void validateAccountSaveRequest(AccountModel request, CrudOperation operation);

    void validateCategorySaveRequest(CategoryModel request, CrudOperation status);

    void validateIngredientSaveRequest(IngredientModel request, CrudOperation status);

    void validateRequestNotNull(RequestModel request, Class entityClass);

    void validateRequestIdNotNull(Long id);

    void validateEntityNotNull(BaseEntity entity, Class entityClass);
}
