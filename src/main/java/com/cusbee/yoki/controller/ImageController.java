package com.cusbee.yoki.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cusbee.yoki.dto.YokiResult;
import com.cusbee.yoki.dto.YokiResult.Status;
import com.cusbee.yoki.entity.Dish;
import com.cusbee.yoki.entity.DishImage;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.exception.BaseException;
import com.cusbee.yoki.model.DishImagesModel;
import com.cusbee.yoki.model.ImageModel;
import com.cusbee.yoki.service.DishService;
import com.cusbee.yoki.service.ImageService;
import com.cusbee.yoki.utils.ErrorCodes;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import scala.actors.threadpool.Arrays;

@RestController
@RequestMapping(value = "images", produces = MediaType.APPLICATION_JSON_VALUE)
public class ImageController {
    @Autowired
    ImageService imageService;

    @Autowired
    DishService dishService;

    @ApiOperation(value = "Uploads dish images")
    @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE}, value = "uploadDishImages")
    public
    @ResponseBody
    YokiResult uploadDishImages(
            @ApiParam(required = true, name = "request") DishImagesModel request) throws IOException {

        if (Objects.isNull(request)) {
            throw new ApplicationException(ErrorCodes.Common.INVALID_REQUEST, "Invalid Request.");
        }
        if (Objects.isNull(request.getImageModelList())) {
            request.setImageModelList(new ArrayList<ImageModel>());
        }
        MultipartFile[] images = request.getImages();
        Long dishId = request.getDishId();
        Dish dish = dishService.get(dishId);
        List<DishImage> dishImages = imageService.saveImagesToServer(Arrays.asList(images), dish);
        return new YokiResult(Status.SUCCESS, "images were successfully uploaded", null);
    }
}