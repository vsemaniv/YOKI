package com.cusbee.yoki.dto;

import com.cusbee.yoki.entity.Order;

public class YokiPosterResponse {
    private String response;
    private Order order;

    public YokiPosterResponse() {
    }

    public YokiPosterResponse(String response, Order order) {
        this.response = response;
        this.order = order;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
