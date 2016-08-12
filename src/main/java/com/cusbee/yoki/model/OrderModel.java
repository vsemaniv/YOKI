package com.cusbee.yoki.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class OrderModel implements RequestModel, Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private Long id;
    private List<DishQuantity> dishes;
    @JsonIgnore
    private Double cost;
    private ClientModel client;
    private Long courierId;
    @JsonIgnore
    private String status;
    private String message;
    private Date timeToTake;
    private Date timeToDeliver;
    private Date timeTaken;
    private Date timeDelivered;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<DishQuantity> getDishes() {
        return dishes;
    }

    public void setDishes(List<DishQuantity> dishes) {
        this.dishes = dishes;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public ClientModel getClient() {
        return client;
    }

    public void setClient(ClientModel client) {
        this.client = client;
    }

    public Long getCourierId() {
        return courierId;
    }

    public void setCourierId(Long courierId) {
        this.courierId = courierId;
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

    public Date getTimeToTake() {
        return timeToTake;
    }

    public void setTimeToTake(Date timeToTake) {
        this.timeToTake = timeToTake;
    }

    public Date getTimeToDeliver() {
        return timeToDeliver;
    }

    public void setTimeToDeliver(Date timeToDeliver) {
        this.timeToDeliver = timeToDeliver;
    }

    public Date getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(Date timeTaken) {
        this.timeTaken = timeTaken;
    }

    public Date getTimeDelivered() {
        return timeDelivered;
    }

    public void setTimeDelivered(Date timeDelivered) {
        this.timeDelivered = timeDelivered;
    }
}
