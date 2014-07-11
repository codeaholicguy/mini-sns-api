/**
 * 
 */
package com.n9.mini.sns.web.websocket.message;

/**
 * @author HoangNN6
 * 
 */
public class BaseMessage {

	public static final String DEVICE_AUTHENTICATION_METHOD = "device_authenticate";
	public static final String REQUIRE_DEVICE_AUTHENTICATION_METHOD = "require_device_authenticate";

	private String method;
	private Object data;

	public BaseMessage() {
	}

	public BaseMessage(String method, Object data) {
		this.method = method;
		this.data = data;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
