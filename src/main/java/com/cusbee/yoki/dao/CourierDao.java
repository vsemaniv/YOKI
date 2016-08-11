package com.cusbee.yoki.dao;

import com.cusbee.yoki.entity.Courier;

import java.util.List;

public interface CourierDao {
    Courier save(Courier courier);

    List<Courier> getAll();
    
    List<Courier> getAllAvailableCourier();

    Courier get(Long id);
}
