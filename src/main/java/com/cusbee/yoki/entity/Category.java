package com.cusbee.yoki.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;



/**
 * 
 * @author Dmytro Khodan
 * @date 09.07.2016
 * @project: yoki
 */

@Entity
@Table(name="category")
<<<<<<< HEAD
public class Category implements Activatable, Serializable{
=======
public class Category implements Serializable{
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	@Column
	private String name;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="category")
	@Fetch(FetchMode.JOIN)
	@JsonIgnore
	private List<Dish> dishes; 
	
	@Column
	@Type(type = "org.hibernate.type.YesNoType")
	private Boolean enabled;
	
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
	
	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public List<Dish> getDishes() {
		return dishes;
	}

	public void setDishes(List<Dish> dishes) {
		this.dishes = dishes;
	}

}
