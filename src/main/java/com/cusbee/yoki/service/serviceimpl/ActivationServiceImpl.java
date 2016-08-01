package com.cusbee.yoki.service.serviceimpl;

import com.cusbee.yoki.entity.Activatable;
import com.cusbee.yoki.service.ActivationService;

public class ActivationServiceImpl implements ActivationService {

    @Override
    public void processActivation(Activatable activatable, boolean activate) {
        activatable.setEnabled(activate);
    }
}
