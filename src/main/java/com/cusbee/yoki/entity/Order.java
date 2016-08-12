package com.cusbee.yoki.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.*;

<<<<<<< HEAD
<<<<<<< HEAD
import com.cusbee.yoki.entity.enums.OrderStatus;
=======
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
=======
import com.cusbee.yoki.entity.enums.OrderStatus;
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


<<<<<<< HEAD
<<<<<<< HEAD
/**
=======

/**
 * 
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
=======
/**
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
 * @author Dmytro Khodan
 * @date 09.07.2016
 * @project: yoki
 */


<<<<<<< HEAD
<<<<<<< HEAD
@Table(name = "orders")
@Entity
public class Order implements BaseEntity, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "ordered_dish",
            joinColumns = {@JoinColumn(name = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "dish_id")})
    @Fetch(FetchMode.JOIN)
    private List<Dish> dishes;

    @Column(name = "order_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar orderDate;

    @Column(name = "order_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "cost")
    private Double cost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    @Fetch(FetchMode.JOIN)
    private Courier courier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courier_id")
    @Fetch(FetchMode.JOIN)
    private Client client;

    @Column(name = "message")
    private String message;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
=======
@Table(name="orders")
=======
@Table(name = "orders")
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
@Entity
public class Order implements BaseEntity, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "ordered_dish",
            joinColumns = {@JoinColumn(name = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "dish_id")})
    @Fetch(FetchMode.JOIN)
    private List<Dish> dishes;

    @Column(name = "order_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar orderDate;

    @Column(name = "order_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "cost")
    private Double cost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    @Fetch(FetchMode.JOIN)
    private Courier courier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courier_id")
    @Fetch(FetchMode.JOIN)
    private Client client;

    @Column(name = "message")
    private String message;
    
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar timeToTake;
    
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar timeTaken;
    
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar timeToDeliver;
    
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar timeDelivered;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    
    public Calendar getTimeToTake() {
		return timeToTake;
	}

	public void setTimeToTake(Calendar timeToTake) {
		this.timeToTake = timeToTake;
	}

	public Calendar getTimeTaken() {
		return timeTaken;
	}

	public void setTimeTaken(Calendar timeTaken) {
		this.timeTaken = timeTaken;
	}

	public Calendar getTimeToDeliver() {
		return timeToDeliver;
	}

	public void setTimeToDeliver(Calendar timeToDeliver) {
		this.timeToDeliver = timeToDeliver;
	}

	public Calendar getTimeDelivered() {
		return timeDelivered;
	}

	public void setTimeDelivered(Calendar timeDelivered) {
		this.timeDelivered = timeDelivered;
	}

	public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
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

    public String getMessage() {
        return message;
    }

<<<<<<< HEAD
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
=======
    public void setMessage(String message) {
        this.message = message;
    }
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
}
