package com.cusbee.yoki.entity;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "order_dish")
@Entity
public class DishQuantity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_id")
    Dish dish;

    @Column
    Integer quantity;

    public DishQuantity(Dish dish, Integer quantity) {
        this.dish = dish;
        this.quantity = quantity;
    }

    public DishQuantity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
