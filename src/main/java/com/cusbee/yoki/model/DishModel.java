package com.cusbee.yoki.model;

import java.io.Serializable;
import java.util.List;

<<<<<<< HEAD
public class DishModel implements RequestModel, Serializable {
=======
public class DishModel implements Serializable {
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
<<<<<<< HEAD
	private Integer weight;
=======
	private Double weight;
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
	private Double price;
	private String description;
	private Long categoryId;
	private String type;
	private List<IngredientModel> ingredients;
<<<<<<< HEAD
	private List<ImageModel> images;
	
	public DishModel(){}
	
	public DishModel(Long id, String name, Integer weight, Double price, String description, Long category_id,
						List<IngredientModel> ingreds, String type, List<ImageModel> images) {
=======
	
	public DishModel(){}
	
	public DishModel(Long id, String name, Double weight, Double price, String description, Long category_id,
						List<IngredientModel> ingreds, String type) {
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
		super();
		this.id = id;
		this.name = name;
		this.weight = weight;
		this.price = price;
		this.description = description;
		this.categoryId = category_id;
		this.ingredients = ingreds;
		this.type = type;
<<<<<<< HEAD
		this.images = images;
	}

	public List<ImageModel> getImages(){
		return images;
	}

	public void setImages(List<ImageModel> images){
		this.images=images;
=======
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
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

<<<<<<< HEAD
	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
=======
	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
		this.weight = weight;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long category_id) {
		this.categoryId = category_id;
	}

	public List<IngredientModel> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<IngredientModel> ingredients) {
		this.ingredients = ingredients;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
