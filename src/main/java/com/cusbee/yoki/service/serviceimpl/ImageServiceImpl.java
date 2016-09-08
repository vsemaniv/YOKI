package com.cusbee.yoki.service.serviceimpl;

import com.cusbee.yoki.entity.Dish;
import com.cusbee.yoki.entity.DishImage;
import com.cusbee.yoki.entity.IdEntity;
import com.cusbee.yoki.entity.enums.EntityType;
import com.cusbee.yoki.entity.enums.ImageType;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.service.DishService;
import com.cusbee.yoki.service.ImageService;
import com.cusbee.yoki.service.ValidatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    //real path to save image
    private static final String BASE_PATH = "/home/yoki/images/";
    //path to get the image
    private static final String ALIAS_PATH = "/yokimages/";

    private static final Logger LOG = LoggerFactory.getLogger(ImageServiceImpl.class);

    @Autowired
    DishService dishService;

    @Autowired
    ValidatorService validatorService;

    @Override
    public IdEntity addImages(List<MultipartFile> images, String type, Long id) {
        if(validatorService.isEnumValid(type, EntityType.class)) {
            Dish dish = dishService.get(id);
            for (MultipartFile image : images) {
                if (!image.isEmpty()) {
                    try {
                        String[] split = image.getOriginalFilename().split("\\.");
                        String extension = split.length > 0 ? split[split.length-1] : "";
                        if(extension.isEmpty() || !validatorService.isEnumValid(extension, ImageType.class)) {
                            throw new ApplicationException(HttpStatus.BAD_REQUEST, "Unsupported image type");
                        }
                        createDirectoriesIfNeeded(type);
                        String relativePath = buildFileName(type, "/", id, "_", System.currentTimeMillis(), ".", extension);
                        image.transferTo(new File(BASE_PATH + relativePath));
                        dish.getImages().add(new DishImage(ALIAS_PATH + relativePath, dish));
                    } catch (IOException e) {
                        throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR,
                                "Problem occurred while writing file to disk. Maybe directories don't exist", e);
                    }
                }
            }
            return dish;
        } else {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "Unknown image type");
        }
    }

    private void createDirectoriesIfNeeded(String type) {
        File file = new File(BASE_PATH + type);
        if(!file.exists()) {
            file.mkdirs();
        }
    }

    private String buildFileName(Object... parts) {
        StringBuilder sb = new StringBuilder();
        for(Object part : parts) {
            sb.append(part);
        }
        return sb.toString();
    }
/*
    public static void main(String[] args) {
        ImageService service = new ImageServiceImpl();
        List<String> imagelist = new ArrayList<>();
        imagelist.add("dddddddddddddddddddddddddddddddrtLOLKOLBASArik");
        Dish dish = new Dish();
        dish.setId(555L);
        List<String> links = service.saveImagesToServer(imagelist, dish);
        System.out.println(links);
        List<String> imagesFromServer = service.getImagesFromServer(links);
        System.out.println(imagesFromServer);
    }*/

}


