package com.cusbee.yoki.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

import com.cusbee.yoki.entity.enums.DishType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;

@Table(name = "dish")
@Entity
public class Dish implements IdEntity, Activatable, Serializable {

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
    private Double price;

    @Column
    private String description;

    @Column
    @Type(type = "org.hibernate.type.YesNoType")
    private Boolean enabled;

    @Column(name = "dish_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private DishType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @Fetch(FetchMode.JOIN)
    private Category category;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dish", orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    private List<DishImage> images;

    @ManyToMany
    @JoinTable(name = "dish_ingredient",
            joinColumns = {@JoinColumn(name = "dish_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "ingredient_id", referencedColumnName = "id")})
    @JsonIgnore
    private List<Ingredient> ingredients;

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<DishImage> getImages() {
        return images;
    }

    public void setImages(List<DishImage> images) {
        this.images = images;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
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
        return type == dish.type && !(category != null ? !category.equals(dish.category) : dish.category != null);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, enabled);
    }
}
