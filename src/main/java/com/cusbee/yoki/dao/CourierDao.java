package com.cusbee.yoki.dao;

import com.cusbee.yoki.entity.Courier;

import java.util.List;

public interface CourierDao {
    Courier save(Courier courier);

    List<Courier> getAll();
<<<<<<< HEAD
=======
    
    List<Courier> getAllAvailableCourier();
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79

    Courier get(Long id);
}
