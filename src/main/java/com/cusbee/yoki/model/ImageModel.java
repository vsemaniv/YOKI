package com.cusbee.yoki.model;

import java.io.Serializable;

<<<<<<< HEAD
public class ImageModel implements RequestModel, Serializable {
=======
public class ImageModel implements Serializable {
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280

	/**
	 * 
	 */
	private static final long serialVersionUID = -3426220289417487763L;
<<<<<<< HEAD
	private Long id;
=======
	private Long imageId;
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
	private String imageURL;
	private String imageName;
	private String imageDescription;
	private String imageUploadedDate;

	public ImageModel() {
	}

	public ImageModel(Long imageId, String imageURL, String imageName,
			String imageDescription, String imageUploadedDate) {
		super();
<<<<<<< HEAD
		this.setId(imageId);
=======
		this.setImageId(imageId);
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
		this.setImageURL(imageURL);
		this.setImageName(imageName);
		this.setImageDescription(imageDescription);
		this.setImageUploadedDate(imageUploadedDate);
	}

<<<<<<< HEAD
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
=======
	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
>>>>>>> 6a48b8fc48bc66f95c794342b107c92154dce280
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImageDescription() {
		return imageDescription;
	}

	public void setImageDescription(String imageDescription) {
		this.imageDescription = imageDescription;
	}

	public String getImageUploadedDate() {
		return imageUploadedDate;
	}

	public void setImageUploadedDate(String imageUploadedDate) {
		this.imageUploadedDate = imageUploadedDate;
	}
	
}
