/**
 * 
 */
package com.n9.mini.sns.domain.dto;

import java.util.List;

/**
 * @author HoangNN6
 * 
 */
public class ImageDto {

	private Integer id;
	private String url;
	private Integer createTime;
	private List<TagDto> tags;

	public ImageDto() {
	}

	public ImageDto(String url) {
		this.url = url;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public List<TagDto> getTags() {
		return tags;
	}

	public void setTags(List<TagDto> tags) {
		this.tags = tags;
	}

}
