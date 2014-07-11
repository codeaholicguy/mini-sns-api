/**
 * 
 */
package com.n9.mini.sns.domain.dto;

import java.util.List;

/**
 * @author HoangNN6
 * 
 */
public class FollowDto {

	private AccountDto account;
	private List<AccountDto> followers;
	private List<AccountDto> followeds;

	public FollowDto() {
	}

	public FollowDto(AccountDto followed) {
		this.account = followed;
	}

	public AccountDto getAccount() {
		return account;
	}

	public void setAccount(AccountDto account) {
		this.account = account;
	}

	public List<AccountDto> getFollowers() {
		return followers;
	}

	public void setFollowers(List<AccountDto> followers) {
		this.followers = followers;
	}

	public List<AccountDto> getFolloweds() {
		return followeds;
	}

	public void setFolloweds(List<AccountDto> followeds) {
		this.followeds = followeds;
	}

}
