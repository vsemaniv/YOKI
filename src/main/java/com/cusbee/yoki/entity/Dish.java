package com.cusbee.yoki.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * @author Dmytro Khodan
 * @date 09.07.2016
 * @project: yoki
 */
@Table(name="dish")
@Entity
public class Dish implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable=false)
	private String name;
	
	@Column
	private Double weight;
	
	@Column
	private Double price;
	
	@Column
	private String description;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="order_id")
	private Order order;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="category_id")
	private Category category;
	
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="dish_ingredients",
			   joinColumns={@JoinColumn(name="dish_id")},
			   inverseJoinColumns={@JoinColumn(name="ingredient_id")})
	private List<Ingredient> ingredients;
	
	@OneToMany(mappedBy="dish")
	private List<DishImage> images;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	
}
