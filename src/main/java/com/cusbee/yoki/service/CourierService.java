package com.cusbee.yoki.service;

import com.cusbee.yoki.entity.Courier;
import com.cusbee.yoki.entity.enums.CrudOperation;
import com.cusbee.yoki.model.CourierModel;

import java.util.List;

public interface CourierService {
<<<<<<< HEAD
=======
	
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
    void remove(Long id);

    Courier get(Long id);

    List<Courier> getAll();

    Courier saveCourier (CourierModel request, CrudOperation operation);

<<<<<<< HEAD
    Courier processActivation(Long id, boolean activate);
=======
    Courier processActivation(Long id, Courier.CourierStatus status);
    
    List<Courier> getAllAvailableCourier();
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
}
