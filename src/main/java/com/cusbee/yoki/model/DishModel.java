package com.cusbee.yoki.model;

import java.io.Serializable;
import java.util.List;

public class DishModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private Double weight;
	private Double price;
	private String description;
	private Long categoryId;
	private String type;
	private List<IngredientModel> ingredients;
	
	public DishModel(){}
	
	public DishModel(Long id, String name, Double weight, Double price, String description, Long category_id,
						List<IngredientModel> ingreds, String type) {
		super();
		this.id = id;
		this.name = name;
		this.weight = weight;
		this.price = price;
		this.description = description;
		this.categoryId = category_id;
		this.ingredients = ingreds;
		this.type = type;
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

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
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
