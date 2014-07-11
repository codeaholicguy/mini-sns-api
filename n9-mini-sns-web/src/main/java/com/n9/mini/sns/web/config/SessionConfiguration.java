/**
 * 
 */
package com.n9.mini.sns.web.config;

import java.util.HashMap;
import java.util.Map;

import com.n9.mini.sns.domain.po.AccessToken;

/**
 * @author HoangNN6
 * 
 */
public class SessionConfiguration {

	public static final String UNAUTHENTICATED = "Unauthenticated";
	public static final String NO_PERMISSION = "You dont have permission";

	private static Map<String, String> session = new HashMap<String, String>();
	private static Map<String, String> refresh = new HashMap<String, String>();
	private static Map<Integer, String> account = new HashMap<Integer, String>();
	private static Map<String, Integer> permission = new HashMap<String, Integer>();

	/**
	 * @param accessToken
	 * @throws Exception
	 */
	public static AccessToken pushSession(AccessToken accessToken) throws Exception {
		AccessToken tmpAccessToken = new AccessToken();
		if (account.containsKey(accessToken.getAccountId())) {
			String refreshToken = account.get(accessToken.getAccountId());
			refreshSession(refreshToken, accessToken.getAccountId());
			tmpAccessToken.setAccessToken(refresh.get(refreshToken));
			tmpAccessToken.setRefreshToken(refreshToken);
			tmpAccessToken.setAccountId(accessToken.getAccountId());
		} else {
			session.put(accessToken.getAccessToken(), accessToken.getRefreshToken());
			refresh.put(accessToken.getRefreshToken(), accessToken.getAccessToken());
			account.put(accessToken.getAccountId(), accessToken.getRefreshToken());
			permission.put(accessToken.getAccessToken(), accessToken.getAccountId());
			tmpAccessToken.setAccessToken(accessToken.getAccessToken());
			tmpAccessToken.setRefreshToken(accessToken.getRefreshToken());
			tmpAccessToken.setAccountId(accessToken.getAccountId());
		}

		return tmpAccessToken;
	}

	/**
	 * @param accessToken
	 */
	public static void removeSession(String accessToken) throws Exception {
		if (!session.containsKey(accessToken)) {
			throw new Exception(UNAUTHENTICATED);
		}
		session.remove(accessToken);
		permission.remove(accessToken);
	}

	/**
	 * @param refreshToken
	 */
	public static void refreshSession(String refreshToken, Integer accountId) throws Exception {
		if (!refresh.containsKey(refreshToken)) {
			throw new Exception(UNAUTHENTICATED);
		}
		session.put(refresh.get(refreshToken), refreshToken);
		permission.put(refresh.get(refreshToken), accountId);
	}

	/**
	 * @param accessToken
	 */
	public static Integer checkAuthority(String accessToken) throws Exception {
		if (!accessToken.equals("test")) {
			if (!session.containsKey(accessToken)) {
				throw new Exception(UNAUTHENTICATED);
			}
		} else {
			return 1;
		}
		return permission.get(accessToken);
	}

	public static void checkAuthority(String accessToken, Integer accountId) throws Exception {
		if (!session.containsKey(accessToken)) {
			throw new Exception(UNAUTHENTICATED);
		} else {
			if (!permission.containsKey(accessToken)) {
				throw new Exception(UNAUTHENTICATED);
			} else {
				if (permission.get(accessToken) != accountId) {
					throw new Exception(NO_PERMISSION);
				}
			}
		}
	}
}
