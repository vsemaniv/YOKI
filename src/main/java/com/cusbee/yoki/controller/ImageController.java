package com.cusbee.yoki.controller;

import com.cusbee.yoki.dto.YokiResult;
import com.cusbee.yoki.entity.Dish;
import com.cusbee.yoki.model.images.MultipleFileModel;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created on 04.09.2016.
 */
@RestController
@RequestMapping(value = "images")
public class ImageController {

    private static final String PATH = "./images/dish";

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public YokiResult<Dish> uploadMultipart(@RequestBody MultipleFileModel fileModel) throws IOException {
        List<MultipartFile> images = fileModel.getImages();
        if (CollectionUtils.isNotEmpty(images)) {
            for (MultipartFile multipartFile : images) {

                String fileName = multipartFile.getOriginalFilename();
                // Handle file content - multipartFile.getInputStream()
                multipartFile.transferTo(new File(PATH + fileName));

            }
        }
        return new YokiResult<>(HttpStatus.OK, "successfully added images to dish", null/*dish here*/);
    }
}
