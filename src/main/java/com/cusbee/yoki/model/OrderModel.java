package com.cusbee.yoki.model;

import java.io.Serializable;
import java.util.List;

import com.cusbee.yoki.entity.Client;

public class OrderModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private List<DishModel> dishes;
	private Double amount;
	private Client client;
	private String message;
	
	
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<DishModel> getDishes() {
		return dishes;
	}
	public void setDishes(List<DishModel> dishes) {
		this.dishes = dishes;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
