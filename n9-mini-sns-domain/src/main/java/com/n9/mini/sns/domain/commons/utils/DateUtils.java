/**
 * 
 */
package com.n9.mini.sns.domain.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author HoangNN6
 * 
 */
public class DateUtils {

	public static final String YYYY_MM_DD = "yyyy/MM/dd";

	/**
	 * @param pattern
	 * @return
	 */
	public static String today(String pattern) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

		return dateFormat.format(calendar.getTime());
	}
}
