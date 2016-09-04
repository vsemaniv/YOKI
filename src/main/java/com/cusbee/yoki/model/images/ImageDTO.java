package com.cusbee.yoki.model.images;

import java.util.List;

public class ImageDTO {

    private Long dishId;
    private List<String> images;

    public ImageDTO() {
    }

    public ImageDTO(Long dishId, List<String> images) {
        this.dishId = dishId;
        this.images = images;
    }

    public Long getDishId() {
        return dishId;
    }

    public void setDishId(Long dishId) {
        this.dishId = dishId;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
