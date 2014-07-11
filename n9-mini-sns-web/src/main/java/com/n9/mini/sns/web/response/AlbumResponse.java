/**
 * 
 */
package com.n9.mini.sns.web.response;

import java.util.List;

/**
 * @author HoangNN6
 * 
 */
public class AlbumResponse {

	String album;
	List<String> files;

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public List<String> getFiles() {
		return files;
	}

	public void setFiles(List<String> files) {
		this.files = files;
	}

}
