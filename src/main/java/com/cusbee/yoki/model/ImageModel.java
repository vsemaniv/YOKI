
package com.cusbee.yoki.model;
import java.io.Serializable;

public class ImageModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3426220289417487763L;
	private Long imageId;
	private String imageURL;
	private String imageName;
	private String imageDescription;
	private String imageUploadedDate;

	public ImageModel() {
	}

	public ImageModel(Long imageId, String imageURL, String imageName,
			String imageDescription, String imageUploadedDate) {
		super();
		this.setImageId(imageId);
		this.setImageURL(imageURL);
		this.setImageName(imageName);
		this.setImageDescription(imageDescription);
		this.setImageUploadedDate(imageUploadedDate);
	}

	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
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