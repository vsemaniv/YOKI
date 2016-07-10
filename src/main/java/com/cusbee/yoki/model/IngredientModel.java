package com.cusbee.yoki.model;

import java.io.Serializable;
import java.util.List;

public class IngredientModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private Double weight;
	private String description;
	
	public IngredientModel() {}
	
	public IngredientModel(Long id, String name, Double weight, List<DishModel> dishes, String description) {
		super();
		this.id = id;
		this.name = name;
		this.weight = weight;
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

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
