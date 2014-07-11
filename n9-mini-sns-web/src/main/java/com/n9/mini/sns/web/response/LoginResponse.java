/**
 * 
 */
package com.n9.mini.sns.web.response;

/**
 * @author HoangNN6
 * 
 */
public class LoginResponse extends BaseResponse {

	public static final String WRONG_ACCOUNT = "Wrong email or password";

	String accessToken;
	String refreshToken;
	Integer accountId;

	public LoginResponse() {
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
