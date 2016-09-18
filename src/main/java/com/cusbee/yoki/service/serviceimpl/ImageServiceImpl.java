package com.cusbee.yoki.service.serviceimpl;

import com.cusbee.yoki.dao.DishDao;
import com.cusbee.yoki.entity.Dish;
import com.cusbee.yoki.entity.DishImage;
import com.cusbee.yoki.entity.IdEntity;
import com.cusbee.yoki.entity.Ingredient;
import com.cusbee.yoki.entity.enums.EntityType;
import com.cusbee.yoki.entity.enums.ImageType;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.service.DishService;
import com.cusbee.yoki.service.ImageService;
import com.cusbee.yoki.service.IngredientService;
import com.cusbee.yoki.service.ValidatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
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
    IngredientService ingredientService;

    @Autowired
    DishDao dishDao;

    @Autowired
    ValidatorService validatorService;

    @Override
    public IdEntity addImages(String type, Long id, MultipartFile... images) {
        if (validatorService.isEnumValid(type, EntityType.class) && images.length > 0) {
            createDirectoriesIfNeeded(type);
            String relativePath;
            switch (EntityType.valueOf(type.toUpperCase())) {
                case DISH:
                    Dish dish = dishService.get(id);
                    for (MultipartFile image : images) {
                        relativePath = saveImage(image, type, id);
                        dish.getImages().add(new DishImage(ALIAS_PATH + relativePath, dish));
                    }
                    return dishService.save(dish);
                case INGREDIENT:
                    Ingredient ingredient = ingredientService.get(id);
                    relativePath = saveImage(images[0], type, id);
                    ingredient.setIconLink(ALIAS_PATH + relativePath);
                    return ingredientService.save(ingredient);
            }
        } else {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "Unknown image type");
        }
        return null;
    }

    @Override
    public List<String> addSliderImages(MultipartFile... images) {
        List<String> sliderLinks = new ArrayList<>();
        for (MultipartFile image : images) {
            String sliderLink = saveImage(image, "slider", 0L);
            sliderLinks.add(sliderLink);
        }
        return sliderLinks;
    }

    @Override
    public void removeDishImages(List<String> links) {
        validatorService.validateLinks(links);
        for (String link : links) {
            removeDishImageEntity(link);
            String linkToDelete = link.replace(ALIAS_PATH, BASE_PATH);
            File file = new File(linkToDelete);
            if (file.exists()) {
                file.delete();
            } else {
                LOG.error("Attempt to delete image on non-existing path. Link: " + linkToDelete);
            }
        }
    }

    @Override
    public void removeIngredientIcon(String link) {
        String linkToDelete = link.replace(ALIAS_PATH, BASE_PATH);
        File file = new File(linkToDelete);
        if (file.exists()) {
            file.delete();
        } else {
            LOG.error("Attempt to delete image on non-existing path. Link: " + linkToDelete);
        }
    }

    /**
     * @param image - multipart with image to save
     * @return relative path to access image
     */
    private String saveImage(MultipartFile image, String type, Long id) {
        if (!image.isEmpty()) {
            try {
                String[] split = image.getOriginalFilename().split("\\.");
                String extension = split.length > 0 ? split[split.length - 1] : "";
                if (extension.isEmpty() || !validatorService.isEnumValid(extension, ImageType.class)) {
                    throw new ApplicationException(HttpStatus.BAD_REQUEST, "Unsupported image type");
                }
                String relativePath = buildFileName(type, "/", id, "_", System.currentTimeMillis(), ".", extension);
                image.transferTo(new File(BASE_PATH + relativePath));
                return relativePath;
            } catch (IOException e) {
                throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Problem occurred while writing file to disk. Maybe directories don't exist", e);
            }
        } else {
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "empty multipart file");
        }
    }

    private void createDirectoriesIfNeeded(String type) {
        File file = new File(BASE_PATH + type);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    private String buildFileName(Object... parts) {
        StringBuilder sb = new StringBuilder();
        for (Object part : parts) {
            sb.append(part);
        }
        return sb.toString();
    }

    private void removeDishImageEntity(String link) {
        DishImage dishImage = dishDao.getDishImageByLink(link);
        validatorService.validateEntityNotNull(dishImage, DishImage.class);
        Dish dish = dishImage.getDish();
        dish.getImages().remove(dishImage);
        dishService.save(dish);
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


