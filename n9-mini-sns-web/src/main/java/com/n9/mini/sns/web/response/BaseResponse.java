/**
 * 
 */
package com.n9.mini.sns.web.response;

/**
 * @author HoangNN6
 * 
 */
public class BaseResponse {

	public static final String SUCCESS = "success";
	public static final String FAIL = "fail";

	private String result;
	private String error;

	public BaseResponse() {
	}

	public BaseResponse(String result, String error) {
		this.result = result;
		this.error = error;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
