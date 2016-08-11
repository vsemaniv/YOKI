package com.cusbee.yoki.service;

import com.cusbee.yoki.entity.Courier;
import com.cusbee.yoki.entity.enums.CrudOperation;
import com.cusbee.yoki.model.CourierModel;

import java.util.List;

public interface CourierService {
	
    void remove(Long id);

    Courier get(Long id);

    List<Courier> getAll();

    Courier saveCourier (CourierModel request, CrudOperation operation);

    Courier processActivation(Long id, Courier.CourierStatus status);
    
    List<Courier> getAllAvailableCourier();
}
