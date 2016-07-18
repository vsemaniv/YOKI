package com.cusbee.yoki.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * 
 * @author Dmytro Khodan
 * @date 09.07.2016
 * @project: yoki
 */

@Entity
@Table(name="ingredient")
public class Ingredient implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Column
	private String name;
	
	@JsonBackReference
	@ManyToMany(mappedBy="ingredients", fetch=FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	private List<Dish> dish;
	
	@Column
	private String description;
	
	@Column(name="ingredient_quantity_type")
	@Enumerated(EnumType.STRING)
	private IngredientQuantityType type;

	@Column(name="value")
	private Double value;
	
	@Column(name="enabled")
	@Type(type = "org.hibernate.type.YesNoType")
	private Boolean enabled;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
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

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public List<Dish> getDish() {
		return dish;
	}

	public void setDish(List<Dish> dish) {
		this.dish = dish;
	}
	
}
