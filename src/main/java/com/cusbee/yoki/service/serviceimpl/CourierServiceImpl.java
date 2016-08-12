package com.cusbee.yoki.service.serviceimpl;

import com.cusbee.yoki.dao.CourierDao;
import com.cusbee.yoki.entity.Courier;
import com.cusbee.yoki.entity.enums.CrudOperation;
<<<<<<< HEAD
=======
import com.cusbee.yoki.exception.ApplicationException;
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
import com.cusbee.yoki.model.CourierModel;
import com.cusbee.yoki.service.ActivationService;
import com.cusbee.yoki.service.CourierService;
import com.cusbee.yoki.service.ValidatorService;
<<<<<<< HEAD
=======
import com.cusbee.yoki.utils.ErrorCodes;

>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
<<<<<<< HEAD
=======
import java.util.Objects;
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79

@Service
public class CourierServiceImpl implements CourierService {

    @Autowired
    CourierDao dao;

    @Autowired
    ValidatorService validatorService;

    @Autowired
    ActivationService activationService;

    @Override
    public Courier get(Long id) {
        validatorService.validateRequestIdNotNull(id);
        Courier courier = dao.get(id);
        validatorService.validateEntityNotNull(courier, Courier.class);
        return courier;
    }

    @Override
    public List<Courier> getAll() {
        return dao.getAll();
    }

    @Override
    public void remove(Long id) {
        //I think we should not ever remove couriers. Instead we could deactivate them.
    }

    @Override
    public Courier saveCourier(CourierModel request, CrudOperation operation) {
        return null;
    }

    @Override
<<<<<<< HEAD
    public Courier processActivation(Long id, boolean activate) {
        return null;
=======
    public Courier processActivation(Long id, Courier.CourierStatus status) {
    	
    	if(Objects.isNull(id)){
    		throw new ApplicationException(ErrorCodes.Common.EMPTY_REQUEST, "Courier id is not present");
    	}
    	Courier courier = get(id);
    	if(Objects.isNull(courier)){
    		throw new ApplicationException(ErrorCodes.Common.EMPTY_REQUEST, "Courier with this id is not present");
    	}
    	courier.setStatus(status);
    	return dao.save(courier);
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
    }

    public void checkForAssignment() {

    }

<<<<<<< HEAD
=======
	@Override
	public List<Courier> getAllAvailableCourier() {
		return dao.getAllAvailableCourier();
	}

>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79

}
