package com.cusbee.yoki.service.serviceimpl;

import com.cusbee.yoki.entity.Dish;
import com.cusbee.yoki.entity.DishImage;
import com.cusbee.yoki.entity.IdEntity;
import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.model.images.ImageDTO;
import com.cusbee.yoki.service.ImageService;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    private static final String BASE_PATH = System.getProperty("home.dir") + "/images/dish";

    static {
        File file = new File(System.getProperty("home.dir")+"/images/dish");
        file.mkdirs();
    }

    private static final Logger LOG = LoggerFactory.getLogger(ImageServiceImpl.class);

    public List<ImageDTO> getImagesForDishes(List<Dish> dishes) {
        List<ImageDTO> imageDTOs = new ArrayList<>();
        for(Dish dish : dishes) {
            List<String> imagesFromServer = getImagesFromServer(dish.getImages());
            imageDTOs.add(new ImageDTO(dish.getId(), imagesFromServer));
        }
        return imageDTOs;
    }

    public List<String> saveImagesToServer(List<String> dishImages) {
        List<String> links = new ArrayList<>();
        for (String dishImage : dishImages) {
            File file = createNewImageFile();
            links.add(file.getAbsolutePath());
            try (OutputStream os = new BufferedOutputStream(new FileOutputStream(file))) {
                byte[] imageData = Base64.decodeBase64(dishImage);
                os.write(imageData);
            } catch (IOException e) {
                LOG.error("Error during saving image on server", e);
                e.printStackTrace();
            }
        }
        return links;
    }

    public List<String> getImagesFromServer(List<DishImage> dishImages) {
        List<String> images = new ArrayList<>();
        for (DishImage image : dishImages) {
            File file = new File(image.getLink());
            if (file.exists()) {
                try(InputStream is = new BufferedInputStream(new FileInputStream(file))) {
                    byte[] data = new byte[is.available()];
                    is.read(data);
                    images.add(Base64.encodeBase64String(data));
                } catch (IOException e) {
                    LOG.error("Error during fetching image from server", e);
                    e.printStackTrace();
                }
            } else {
                throw new ApplicationException(HttpStatus.NOT_FOUND, "File doesn't exist or link is broken");
            }
        }
        return images;
    }

    private File createNewImageFile() {
        StringBuilder sb = new StringBuilder(BASE_PATH)
                .append(System.currentTimeMillis())
                .append(".png");
        File file = new File(sb.toString());
        try {
            file.createNewFile();
            return file;
        } catch (IOException e) {
            LOG.error("Error during creating new file on server", e);
            throw new ApplicationException("Error during creating new file on server", e);
        }
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


