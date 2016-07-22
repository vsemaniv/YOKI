package com.cusbee.yoki.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class DishImagesModel implements Serializable{
	private static final long serialVersionUID = -1224220557083937587L;
	private Long dishId;
	private List<ImageModel> imageModelList;
	private MultipartFile[] images;
	public DishImagesModel() {

	}

	public Long getDishId() {
		return dishId;
	}

	public void setDishId(Long dishId) {
		this.dishId = dishId;
	}

	public List<ImageModel> getImageModelList() {
		return imageModelList;
	}

	public void setImageModelList(List<ImageModel> imageModelList) {
		this.imageModelList = imageModelList;
	}

	public MultipartFile[] getImages() {
		return images;
	}

	public void setImages(MultipartFile[] images) {
		this.images = images;
	}

	public DishImagesModel(Long dishId, MultipartFile[] images) {
		super();
		this.dishId = dishId;
		this.images = images;
	}
	
	public DishImagesModel(Long dishId, List<ImageModel> imageModelList) {
		super();
		this.dishId = dishId;
		this.imageModelList = imageModelList;
	}
}
