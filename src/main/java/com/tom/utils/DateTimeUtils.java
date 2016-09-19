package com.tom.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author TommyDeng <250575979@qq.com>
 * @version 创建时间：2016年9月13日 下午3:22:17
 *
 */
public class DateTimeUtils {

	/**
	 * 获取指定日期的指定格式的字符串
	 * 
	 * @param date
	 * @param dateFormat
	 * @return
	 */
	public static String getFormatStringByDate(Date date, String dateFormat) {
		return new SimpleDateFormat(dateFormat).format(date);
	}

	/**
	 * 获取当前日期的指定格式的字符串
	 * 
	 * @param dateFormat
	 * @return
	 */
	public static String getFormatStringByDate(String dateFormat) {
		return getFormatStringByDate(Calendar.getInstance().getTime(), dateFormat);
	}
}
