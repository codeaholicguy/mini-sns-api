/**
 * 
 */
package com.n9.mini.sns.domain.commons.utils;

/**
 * @author HoangNN6
 * 
 */
public class StringUtils {

	public static final String EMPTY = "";
	public static final String NULL = "null";
	public static final String SLASH = "/";
	public static final String DASH = "_";
	public static final String COLON = ",";
	public static final String HYPHEN = "-";
	public static final String DOT = ".";
	public static final String SPACE = " ";

	public static boolean isNullorEmpty(String string) {
		if (string == null || string.equalsIgnoreCase(NULL) || string.equals(EMPTY)) {
			return true;
		}
		return false;
	}

}
