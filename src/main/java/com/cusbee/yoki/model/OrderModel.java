package com.cusbee.yoki.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class OrderModel implements RequestModel, Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private List<DishQuantityModel> dishes;
    @JsonIgnore
    private Double cost;
    private ClientModel client;
    private Long courierId;
    private Boolean closed;
    private String status;
    private String message;
    private String timeToDeliver;
    private String timeTaken;
    private String timeDelivered;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<DishQuantityModel> getDishes() {
        return dishes;
    }

    public void setDishes(List<DishQuantityModel> dishes) {
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

    public String getTimeToDeliver() {
        return timeToDeliver;
    }

    public void setTimeToDeliver(String timeToDeliver) {
        this.timeToDeliver = timeToDeliver;
    }

    public String getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(String timeTaken) {
        this.timeTaken = timeTaken;
    }

    public String getTimeDelivered() {
        return timeDelivered;
    }

    public void setTimeDelivered(String timeDelivered) {
        this.timeDelivered = timeDelivered;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }
}
