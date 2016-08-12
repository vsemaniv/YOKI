package com.cusbee.yoki.model.poster;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created on 11.08.2016.
 */
public class WriteOffDish implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("type")
    private String type;

    @JsonProperty("weight")
    private Integer quantity;

    public WriteOffDish(Integer id, String type, Integer quantity) {
        this.id = id;
        this.type = type;
        this.quantity = quantity;
    }

    public WriteOffDish() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "WriteOffDish{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }
}
