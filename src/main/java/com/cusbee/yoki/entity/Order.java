package com.cusbee.yoki.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;



/**
 * 
 * @author Dmytro Khodan
 * @date 09.07.2016
 * @project: yoki
 */


@Table(name="orders")
@Entity
public class Order implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinTable(name="ordered_dish",
			   joinColumns={@JoinColumn(name="order_id")},
			   inverseJoinColumns={@JoinColumn(name="dish_id")})
	@Fetch(FetchMode.JOIN)
	private List<Dish> dishes;
	
	@Column(name="order_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar orderDate;
	
	@Column(name="order_status", nullable=false)
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	@Column(name="amount")
	private Double amount;
	
	@Column(name="first_name", length=35, nullable=false)
	private String firstName;
	
	@Column(name="second_name", length=35, nullable=false)
	private String secondName;
	
	@Column(name="phone_number", length=35, nullable=false)
	private String phoneNumber;
	
	@Column(name="location", length=35, nullable=false)
	private String location;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public List<Dish> getDishes() {
		return dishes;
	}

	public void setDishes(List<Dish> dishes) {
		this.dishes = dishes;
	}

	public Calendar getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Calendar orderDate) {
		this.orderDate = orderDate;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
}
