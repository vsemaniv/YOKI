package com.cusbee.yoki.service;

import com.cusbee.yoki.entity.IdEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    IdEntity addImages(String type, Long id, MultipartFile... images);

    List<String> addSliderImages(MultipartFile... images);

    void removeDishImages(List<String> links);
}
