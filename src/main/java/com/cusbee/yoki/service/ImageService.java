package com.cusbee.yoki.service;
import com.cusbee.yoki.entity.Dish;
import com.cusbee.yoki.entity.DishImage;
import com.cusbee.yoki.entity.IdEntity;
import com.cusbee.yoki.model.images.ImageDTO;

import java.util.List;

public interface ImageService {

    List<String> saveImagesToServer(List<String> dishImages);

    List<String> getImagesFromServer(List<DishImage> dishImages);

    List<ImageDTO> getImagesForDishes(List<Dish> dishes);
}
