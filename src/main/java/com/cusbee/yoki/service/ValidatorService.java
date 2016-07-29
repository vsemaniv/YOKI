package com.cusbee.yoki.service;

import com.cusbee.yoki.entity.BaseEntity;
import com.cusbee.yoki.entity.CrudOperation;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.AccountModel;
import com.cusbee.yoki.model.CategoryModel;
import com.cusbee.yoki.model.RequestModel;

/**

 */
public interface ValidatorService {

    void validateAccountParseRequest(AccountModel request, CrudOperation operation) throws BaseException;

    void validateCategory(CategoryModel request, CrudOperation status) throws BaseException;

    void validateRequestNotNull(RequestModel request) throws BaseException;

    void validateRequestIdNotNull(Long id) throws BaseException;

    void validateEntityNotNull(BaseEntity entity, Class entityClass) throws BaseException;
}
