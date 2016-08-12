package com.cusbee.yoki.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cusbee.yoki.entity.Dish;
import com.cusbee.yoki.entity.DishImage;
import com.cusbee.yoki.exception.BaseException;

public interface ImageService {

    void save(DishImage dishImage);

    void remove(DishImage dishImage);

    DishImage get(Long id);

    List<DishImage> getAll();

    List<DishImage> saveImagesToServer(List<MultipartFile> images,
                                       Dish dish) throws IOException;

}