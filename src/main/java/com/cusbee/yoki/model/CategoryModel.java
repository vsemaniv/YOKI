package com.cusbee.yoki.model;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;


<<<<<<< HEAD
<<<<<<< HEAD
public class CategoryModel implements RequestModel, Serializable {
=======
public class CategoryModel implements Serializable {
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
=======
public class CategoryModel implements RequestModel, Serializable {
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79

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
