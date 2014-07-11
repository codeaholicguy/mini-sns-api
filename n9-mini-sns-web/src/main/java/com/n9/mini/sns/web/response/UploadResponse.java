/**
 * 
 */
package com.n9.mini.sns.web.response;

import java.util.List;

/**
 * @author HoangNN6
 * 
 */
public class UploadResponse extends BaseResponse {

	List<Integer> imageIds;
	List<String> fileNames;
	List<String> links;
	String album;

	public UploadResponse() {
	}

	public List<Integer> getImageIds() {
		return imageIds;
	}

	public void setImageIds(List<Integer> imageIds) {
		this.imageIds = imageIds;
	}

	public List<String> getFileNames() {
		return fileNames;
	}

	public List<String> getLinks() {
		return links;
	}

	public void setLinks(List<String> links) {
		this.links = links;
	}

	public void setFileNames(List<String> fileNames) {
		this.fileNames = fileNames;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

}
