package com.cusbee.yoki.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created on 12.09.2016.
 */
@Table(name = "ingredient")
@Entity
public class Ingredient implements IdEntity, Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private String iconLink;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconLink() {
        return iconLink;
    }

    public void setIconLink(String iconLink) {
        this.iconLink = iconLink;
    }
}
