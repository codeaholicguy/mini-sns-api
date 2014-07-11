/**
 * 
 */
package com.n9.mini.sns.web.websocket.message;


/**
 * @author HoangNN6
 * 
 */
public class ErrorMessage {

	public static final String DEVICE_HAS_BEEN_REGISTED_CODE = "001";
	public static final String DEVICE_HAS_BEEN_REGISTED_MESSAGE = "Device has been registed";
	public static final String DEVICE_REGISTER_SUCCESSFUL_CODE = "002";
	public static final String DEVICE_REGISTER_SUCCESSFUL_MESSAGE = "Device register successful";

	private String code;
	private String message;

	public ErrorMessage(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
