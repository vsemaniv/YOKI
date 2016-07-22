package com.cusbee.yoki.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cusbee.yoki.entity.CrudOperation;
import com.cusbee.yoki.entity.Dish;
import com.cusbee.yoki.entity.DishImage;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.ImageModel;
public interface ImageService {
	
	void add(DishImage dishImage);

	void update(DishImage dishImage);

	void remove(DishImage dishImage) throws BaseException;

	DishImage get(Long id) throws BaseException;

	List<DishImage> getAll();

	List<DishImage> saveImagesToServer(List<MultipartFile> images,
			Dish dish) throws BaseException, IOException;
	
}

