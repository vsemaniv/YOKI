package com.cusbee.yoki.controller;

import com.cusbee.yoki.entity.Dish;
import com.cusbee.yoki.model.images.GetImageDTO;
import com.cusbee.yoki.model.images.ImageDTO;
import com.cusbee.yoki.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "images")
public class ImageController {

    @Autowired
    ImageService imageService;

    @RequestMapping(value = "getDishImages", method = RequestMethod.POST)
    public List<ImageDTO> getDishImages(@RequestBody List<GetImageDTO> dishes) {
        return imageService.getImagesForDishes(dishes);
    }
}
