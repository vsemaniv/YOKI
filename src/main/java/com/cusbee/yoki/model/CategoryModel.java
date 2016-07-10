package com.cusbee.yoki.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class CategoryModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private List<DishModel> dishes;
	
	public CategoryModel() {}
	
	public CategoryModel(Long id, String name, List<DishModel> dishes, MultipartFile[] images){
		super();
		this.id=id;
		this.name=name;
		this.dishes = dishes;
	}
	
	public List<DishModel> getDishes() {
		return dishes;
	}

	public void setDishes(List<DishModel> dishes) {
		this.dishes = dishes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
