/**
 * 
 */
package com.n9.mini.sns.web.websocket.message;

import java.util.Map;

/**
 * @author HoangNN6
 * 
 */
public class DeviceAuthenticationMessage {

	public static final String NO_DEVICE_ID = "na";
	public static final String DEVICE_ID = "deviceId";

	private String deviceId;

	public DeviceAuthenticationMessage() {
		this.deviceId = NO_DEVICE_ID;
	}

	public DeviceAuthenticationMessage(String deviceId) {
		this.deviceId = deviceId;
	}

	public DeviceAuthenticationMessage(Map<String, String> data) {
		this.deviceId = data.get(DEVICE_ID);
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

}
