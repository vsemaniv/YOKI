package com.cusbee.yoki.service.serviceimpl;

import com.cusbee.yoki.dao.CourierDao;
import com.cusbee.yoki.entity.Courier;
import com.cusbee.yoki.entity.CrudOperation;
import com.cusbee.yoki.model.CourierModel;
import com.cusbee.yoki.service.ActivationService;
import com.cusbee.yoki.service.CourierService;
import com.cusbee.yoki.service.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Courier processActivation(Long id, boolean activate) {
        return null;
    }
}
