package com.cusbee.yoki.controller;

import com.cusbee.yoki.dto.YokiResult;
import com.cusbee.yoki.entity.IdEntity;
import com.cusbee.yoki.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartRequest;
@RestController
@RequestMapping(value = "images")
public class ImageController {

    @Autowired
    ImageService imageService;

    @RequestMapping(value = "saveImages", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public YokiResult<IdEntity> addImages(@RequestParam(value = "id") Long id,
                                           @RequestParam(value = "type") String type,
                                           MultipartRequest request) {
        IdEntity idEntity = imageService.addImages(request.getFiles("files[]"), type, id);
        return new YokiResult<>(HttpStatus.OK, "Images have been added successfully", idEntity);
    }
}
