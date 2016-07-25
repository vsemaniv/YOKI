package com.cusbee.yoki.model;

import java.io.Serializable;
import java.util.List;

import com.cusbee.yoki.entity.IngredientQuantityType;

public class IngredientModel implements RequestModel, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private Double value;
	private IngredientQuantityType type;
	private String description;
	
	public IngredientModel() {}
	
	public IngredientModel(Long id, String name, Double weight, List<DishModel> dishes, String description) {
		super();
		this.id = id;
		this.name = name;
		this.value = weight;
		this.description = description;
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

	public Double getValue() {
		return value;
	}

	public void setValue(Double weight) {
		this.value = weight;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public IngredientQuantityType getType() {
		return type;
	}

	public void setType(IngredientQuantityType type) {
		this.type = type;
	}
	
}
