package com.cusbee.yoki.service;

import com.cusbee.yoki.entity.Activatable;

public interface ActivationService {
        void processActivation(Activatable activatable, boolean activate);
}
