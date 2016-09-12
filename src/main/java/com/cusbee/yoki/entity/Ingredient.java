package com.cusbee.yoki.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created on 12.09.2016.
 */

@Entity
@Table(name = "ingredient")
public class Ingredient implements BaseEntity, Serializable {

    @Id
    @GeneratedValue
    Long id;

    @Column
    String name;

    @Column
    String iconLink;



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
