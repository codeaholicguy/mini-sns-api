/**
 * 
 */
package com.n9.mini.sns.domain.dto;

/**
 * @author HoangNN6
 * 
 */
public class TagDto {

	private Integer id;
	private String name;

	public TagDto() {
	}

	public TagDto(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
