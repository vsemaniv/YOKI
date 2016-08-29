package com.cusbee.yoki.dao;

import com.cusbee.yoki.entity.CourierDetails;

import java.util.List;

public interface CourierDao {
    CourierDetails save(CourierDetails courierDetails);

    List<CourierDetails> getAllCouriers();
    
    List<CourierDetails> getAvailableCouriers();

    CourierDetails get(Long id);
}
