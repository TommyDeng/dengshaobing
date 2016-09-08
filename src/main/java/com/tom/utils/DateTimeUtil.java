package com.tom.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {

	/**
	 * 获取指定日期的字符串
	 * 
	 * @param date
	 * @param dateFormat
	 * @return
	 */
	public static String getFormatStringByDate(Date date, String dateFormat) {
		return new SimpleDateFormat(dateFormat).format(date);
	}

}
