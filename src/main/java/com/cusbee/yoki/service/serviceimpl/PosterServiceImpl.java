package com.cusbee.yoki.service.serviceimpl;

import com.cusbee.yoki.entity.Order;
import com.cusbee.yoki.service.StorageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PosterServiceImpl implements StorageService {

    @Override
    public boolean writeOffOrder(Order order) {
        return false;
    }

    /**
     * takes a list of dish names
     */
    private List<String> getDishes(Order order) {
        return null;
    }
}
