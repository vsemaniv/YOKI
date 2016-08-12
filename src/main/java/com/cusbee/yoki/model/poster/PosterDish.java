package com.cusbee.yoki.model.poster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created on 11.08.2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PosterDish implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("product_id")
    private Integer id;

    @JsonProperty("product_name")
    private String name;

    @JsonProperty("type")
    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "PosterDish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}