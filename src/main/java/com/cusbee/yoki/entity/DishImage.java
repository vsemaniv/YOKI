package com.cusbee.yoki.entity;

import java.io.Serializable;

import javax.persistence.*;

@Table(name = "dish_image")
@Entity
public class DishImage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String link;

    @ManyToOne
    private Dish dish;

    public DishImage(String link, Dish dish) {
        this.link = link;
        this.dish = dish;
    }

    public DishImage() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }
}
