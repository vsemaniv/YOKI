package com.cusbee.yoki.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

<<<<<<< HEAD
<<<<<<< HEAD

public class OrderModel implements RequestModel, Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private List<DishQuantity> dishes;
    private Double amount;
    private ClientModel client;
    private Long courierId;
    private String status;
    private String message;

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

    public List<DishQuantity> getDishes() {
        return dishes;
    }

    public void setDishes(List<DishQuantity> dishes) {
        this.dishes = dishes;
    }

    public Long getCourierId() {
        return courierId;
    }

    public void setCourierId(Long courierId) {
        this.courierId = courierId;
    }

    public ClientModel getClient() {
        return client;
    }

    public void setClient(ClientModel client) {
        this.client = client;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
=======
import com.cusbee.yoki.entity.Client;
=======
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79

import com.fasterxml.jackson.annotation.JsonIgnore;


public class OrderModel implements RequestModel, Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private Long id;
    private List<DishQuantity> dishes;
    @JsonIgnore
    private Double amount;
    private ClientModel client;
    private Long courierId;
    @JsonIgnore
    private String status;
    private String message;
    private Calendar timeToTake;
    private Calendar timeTaken;
    private Calendar timeToDeliver;
    private Calendar timeDelivered;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
<<<<<<< HEAD
	
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
=======

	public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public List<DishQuantity> getDishes() {
        return dishes;
    }

    public void setDishes(List<DishQuantity> dishes) {
        this.dishes = dishes;
    }

    public Long getCourierId() {
        return courierId;
    }

    public void setCourierId(Long courierId) {
        this.courierId = courierId;
    }

    public ClientModel getClient() {
        return client;
    }

    public void setClient(ClientModel client) {
        this.client = client;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
>>>>>>> a1ca618150c7cc0f0bc579c0c4285aea328e9e79
}
