/**
 * 
 */
package com.n9.mini.sns.web.response;

import java.util.List;

/**
 * @author HoangNN6
 * 
 */
public class AlbumsResponse extends BaseResponse {

	List<AlbumResponse> albums;

	public List<AlbumResponse> getAlbums() {
		return albums;
	}

	public void setAlbums(List<AlbumResponse> albums) {
		this.albums = albums;
	}

}
