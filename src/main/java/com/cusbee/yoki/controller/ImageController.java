package com.cusbee.yoki.controller;

import com.cusbee.yoki.dto.YokiResult;
import com.cusbee.yoki.entity.IdEntity;
import com.cusbee.yoki.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartRequest;

import java.util.List;

@RestController
@RequestMapping(value = "images")
public class ImageController {

    @Autowired
    ImageService imageService;

    @RequestMapping(value = "addImages", method = RequestMethod.POST, consumes = {"multipart/form-data", "application/json"})
    public YokiResult<IdEntity> addImages(@RequestParam(value = "id") Long id,
                                           @RequestParam(value = "type") String type,
                                           MultipartRequest request) {
        IdEntity idEntity = imageService.addImages(request.getFiles("images[]"), type, id);
        return new YokiResult<>(HttpStatus.OK, "Images have been added successfully", idEntity);
    }

    @RequestMapping(value = "removeImages", method = RequestMethod.POST)
    public YokiResult<IdEntity> removeImages(@RequestBody List<String> links) {
        imageService.removeImages(links);
        return new YokiResult<>(HttpStatus.OK, "Images have been removed successfully", null);
    }
}
