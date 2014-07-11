/**
 * 
 */
package com.n9.mini.sns.web.websocket.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.n9.mini.sns.web.websocket.message.BaseMessage;
import com.n9.mini.sns.web.websocket.message.DeviceAuthenticationMessage;
import com.n9.mini.sns.web.websocket.message.ErrorMessage;

/**
 * @author HoangNN6
 * 
 */
public class WebsocketEndPoint extends TextWebSocketHandler {

	private static final Log LOGGER = LogFactory.getLog(WebsocketEndPoint.class);
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	private TaskScheduler SCHEDULER = new ConcurrentTaskScheduler();

	private static Map<WebSocketSession, Integer> queue = new HashMap<WebSocketSession, Integer>();
	private static Map<String, WebSocketSession> sessions = new HashMap<String, WebSocketSession>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.socket.handler.AbstractWebSocketHandler#
	 * handleTextMessage(org.springframework.web.socket.WebSocketSession,
	 * org.springframework.web.socket.TextMessage)
	 */
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		super.handleTextMessage(session, message);

		BaseMessage recieveMessage = OBJECT_MAPPER.readValue(message.getPayload(), BaseMessage.class);

		if (recieveMessage.getMethod().equals(BaseMessage.DEVICE_AUTHENTICATION_METHOD)) {
			String deviceId = (new DeviceAuthenticationMessage((Map<String, String>) recieveMessage.getData())).getDeviceId();
			if (!sessions.containsKey(deviceId)) {
				LOGGER.info("Session: " + session.getId() + " authenticate device: " + deviceId + " success, remove from queue");
				sessions.put(deviceId, session);

				ErrorMessage errorMessage = new ErrorMessage(ErrorMessage.DEVICE_REGISTER_SUCCESSFUL_CODE, ErrorMessage.DEVICE_REGISTER_SUCCESSFUL_MESSAGE);

				BaseMessage authenticationMessage = new BaseMessage(BaseMessage.DEVICE_AUTHENTICATION_METHOD, errorMessage);

				TextMessage returnMessage = new TextMessage(OBJECT_MAPPER.writeValueAsString(authenticationMessage));

				queue.remove(session);
				session.sendMessage(returnMessage);
			} else {
				LOGGER.info("Session: " + session.getId() + " authenticate device: " + deviceId + " fail. DeviceId has been registed");
				ErrorMessage errorMessage = new ErrorMessage(ErrorMessage.DEVICE_HAS_BEEN_REGISTED_CODE, ErrorMessage.DEVICE_HAS_BEEN_REGISTED_MESSAGE);

				BaseMessage authenticationMessage = new BaseMessage(BaseMessage.DEVICE_AUTHENTICATION_METHOD, errorMessage);

				TextMessage returnMessage = new TextMessage(OBJECT_MAPPER.writeValueAsString(authenticationMessage));

				session.sendMessage(returnMessage);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.socket.handler.AbstractWebSocketHandler#
	 * afterConnectionEstablished
	 * (org.springframework.web.socket.WebSocketSession)
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		BaseMessage requireAuthenticationMessage = new BaseMessage(BaseMessage.REQUIRE_DEVICE_AUTHENTICATION_METHOD, new DeviceAuthenticationMessage());

		TextMessage returnMessage = new TextMessage(OBJECT_MAPPER.writeValueAsString(requireAuthenticationMessage));
		LOGGER.info("Require session: " + session.getId() + " authenticate device, put it to queue");
		session.sendMessage(returnMessage);
		queue.put(session, (int) System.currentTimeMillis());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.socket.handler.AbstractWebSocketHandler#
	 * afterConnectionClosed(org.springframework.web.socket.WebSocketSession,
	 * org.springframework.web.socket.CloseStatus)
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		LOGGER.info("Close session: " + session.getId());
		if (sessions.containsValue(session)) {
			sessions.values().remove(session);
		}
	}

	/**
	 * Invoked after bean creation is complete, this method will schedule
	 * updatePriceAndBroacast every 1 second
	 */
	@PostConstruct
	private void broadcastTimePeriodically() {
		SCHEDULER.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				LOGGER.info("Clean queue");
				cleanQueue();
			}
		}, 120000);
	}

	/**
	 * @param message
	 * @param deviceIds
	 * @throws IOException
	 */
	public static void pushMessage(String message, List<String> deviceIds) throws IOException {
		if (sessions != null && sessions.size() > 0) {
			TextMessage returnMessage = new TextMessage(message);
			for (String deviceId : deviceIds) {
				LOGGER.info("Push message to device: " + deviceId);
				if (sessions.get(deviceId) != null) {
					sessions.get(deviceId).sendMessage(returnMessage);
				}
			}
		}
	}

	/**
	 * Clean queue.
	 */
	private void cleanQueue() {
		int now = (int) System.currentTimeMillis();
		int count = 0;
		Iterator<Entry<WebSocketSession, Integer>> it = queue.entrySet().iterator();
		while (it.hasNext()) {
			Entry<WebSocketSession, Integer> entry = it.next();
			if (now - entry.getValue() > 60) {
				try {
					entry.getKey().close();
					it.remove();
					count++;
				} catch (IOException e) {
					LOGGER.error("Close session: " + entry.getKey().getId() + " fail - Error: " + e.getMessage());
				}
			}
		}
		LOGGER.info("Clean queue - " + count + " session is closed");
	}
}
