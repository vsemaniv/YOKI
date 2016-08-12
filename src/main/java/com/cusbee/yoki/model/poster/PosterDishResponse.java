package com.cusbee.yoki.model.poster;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created on 11.08.2016.
 */

public class PosterDishResponse {

    @JsonProperty("response")
    private List<PosterDish> posterDishes;

    public List<PosterDish> getPosterDishes() {
        return posterDishes;
    }

    public void setPosterDishes(List<PosterDish> posterDishes) {
        this.posterDishes = posterDishes;
    }
}
