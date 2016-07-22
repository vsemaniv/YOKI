package com.cusbee.yoki.serviceimpl;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cusbee.yoki.dao.DishDao;
import com.cusbee.yoki.dao.ImageDao;

import com.cusbee.yoki.entity.CrudOperation;
import com.cusbee.yoki.entity.Dish;
import com.cusbee.yoki.entity.DishImage;

import com.cusbee.yoki.exception.ApplicationException;
import com.cusbee.yoki.exception.BaseException;

import com.cusbee.yoki.model.ImageModel;
import com.cusbee.yoki.service.ImageService;
import com.cusbee.yoki.service.NullPointerService;
import com.cusbee.yoki.utils.ErrorCodes;

@Service
public class ImageServiceImpl implements ImageService{
	
	@Value("${empty_request}")
	public String EMPTY_REQUEST;
	
	@Value("${invalid_request}")
	public String INVALID_REQUEST;
	
	@Autowired
	ImageDao imageDao;
	
	@Autowired
	DishDao dishDao;
	
	@Autowired
	NullPointerService nullPointerService;
	

	@Override
	public void add(DishImage dishImage) {
		this.imageDao.add(dishImage);
		
	}

	@Override
	public void update(DishImage dishImage) {
		this.imageDao.update(dishImage);
		
	}

	@Override
	public void remove(DishImage dishImage) throws BaseException {
		this.imageDao.remove(dishImage);
		
	}

	@Override
	public DishImage get(Long id) throws BaseException {
		return this.imageDao.get(id);
	}

	@Override
	public List<DishImage> getAll() {
		return this.imageDao.getAll();
	}
	
	

	@Override
	public List<DishImage> saveImagesToServer(List<MultipartFile> images, Dish dish) throws BaseException, IOException {
		String imageServerHost = "/home/yoki/images";
		File dir = new File(imageServerHost + File.separator  + dish.getId());
		if (!dir.exists())
			dir.mkdirs();
		StringBuilder fileName = new StringBuilder(dish.getName());
		long datetime = Calendar.getInstance().getTimeInMillis();
		fileName.append(datetime);
		fileName.append("_");
		List<DishImage> imagesList = new ArrayList<DishImage>();
		DishImage dishImage = null;
		File serverFile = null;
		// Iterate list of images from request and validate.
		for (MultipartFile image : images) {
			if (Objects.isNull(image) || image.isEmpty()) {
				throw new ApplicationException(ErrorCodes.Image.INVALID_REQUEST,
						"One of the entry in a image list has null value. Please correct it");
			}
			
			fileName.append(image.getOriginalFilename());
			// save the file into image server directory.
			// Create the file on server
			serverFile = new File(dir.getAbsolutePath() + File.separator + image.getOriginalFilename());
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
			stream.write(image.getBytes());
			stream.close();
			

			// Save the id you have used to create the file name in the DB. You
			// can retrieve the image in future with the ID.
			dishImage = new DishImage();
			dishImage.setName(image.getOriginalFilename());
			dishImage.setDish(dish);
			dishImage.setLocation(serverFile.getAbsolutePath());
			imagesList.add(dishImage);
		}
		return imagesList;
	}

}
