package com.cusbee.yoki.model.images;

import java.util.List;

public class GetImageDTO {
    private Long dishId;
    private List<String> links;

    public GetImageDTO() {
    }

    public GetImageDTO(Long dishId, List<String> links) {
        this.dishId = dishId;
        this.links = links;
    }

    public Long getDishId() {
        return dishId;
    }

    public void setDishId(Long dishId) {
        this.dishId = dishId;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }
}
