/**
 * 
 */
package com.n9.mini.sns.domain.dto;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * @author HoangNN6
 * 
 */
public class AccountDto {

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	private Integer id;
	private String email;
	private String password;
	private String screenName;
	private String avatar;
	private Boolean followed;

	public AccountDto() {
	}

	public AccountDto(String email) {
		this.email = email;
	}

	public AccountDto(Integer id) {
		this.id = id;
	}

	public AccountDto(String email, String password, String screenName, String avatar) {
		this.email = email;
		this.password = password;
		this.screenName = screenName;
		this.avatar = avatar;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public Boolean getFollowed() {
		return followed;
	}

	public void setFollowed(Boolean followed) {
		this.followed = followed;
	}
}
