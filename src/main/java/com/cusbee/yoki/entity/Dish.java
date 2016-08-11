package com.cusbee.yoki.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import com.cusbee.yoki.entity.enums.DishType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;


/**
 * @author Dmytro Khodan
 * @date 09.07.2016
 * @project: yoki
 */
@Table(name = "dish")
@Entity
public class Dish implements Activatable, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private Double weight;

    @Column
    private Double price;

    @Column
    private String description;

    @Column
    @Type(type = "org.hibernate.type.YesNoType")
    private Boolean enabled;

    @Column(name = "dish_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private DishType type;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "dishes")
    @Fetch(FetchMode.JOIN)
    private List<Order> order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @Fetch(FetchMode.JOIN)
    private Category category;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dish")
    @Fetch(FetchMode.JOIN)
    @JsonManagedReference
    private List<DishImage> images;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public DishType getType() {
        return type;
    }

    public void setType(DishType type) {
        this.type = type;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<DishImage> getImages() {
        return this.images;
    }

    public void setImages(List<DishImage> images) {
        this.images = images;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dish dish = (Dish) o;

        if (!id.equals(dish.id)) return false;
        if (!name.equals(dish.name)) return false;
        if (description != null ? !description.equals(dish.description) : dish.description != null) return false;
        if (!enabled.equals(dish.enabled)) return false;
        if (type != dish.type) return false;
        return !(category != null ? !category.equals(dish.category) : dish.category != null);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + enabled.hashCode();
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }
}
