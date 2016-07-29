package com.cusbee.yoki.dao;

import java.util.List;

import com.cusbee.yoki.entity.DishImage;

public interface ImageDao {
    void save(DishImage dishImage);

    DishImage get(Long id);

    List<DishImage> getAll();

    void remove(DishImage dishImage);
}