/**
 * 
 */
package com.n9.mini.sns.domain.po;

/**
 * @author HoangNN6
 * 
 */
public class AccessToken {

	String accessToken;
	String refreshToken;
	Integer accountId;

	public AccessToken() {
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

}
