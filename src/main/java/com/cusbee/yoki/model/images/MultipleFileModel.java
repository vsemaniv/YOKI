package com.cusbee.yoki.model.images;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created on 04.09.2016.
 */
public class MultipleFileModel {

    private String imageId;
    private List<MultipartFile> images;

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public List<MultipartFile> getImages() {
        return images;
    }

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }
}
