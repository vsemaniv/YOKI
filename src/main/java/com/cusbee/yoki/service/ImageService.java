package com.cusbee.yoki.service;

import com.cusbee.yoki.entity.IdEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    IdEntity addImages(List<MultipartFile> images, String type, Long id);

    void removeImages(List<String> links);
}
