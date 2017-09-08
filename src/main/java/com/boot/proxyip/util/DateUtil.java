package com.boot.proxyip.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author 王如雨
 *
 */
public class DateUtil {
	public static final String NO_TIME_LINE_FORMAT = "yyyy-MM-dd";
	public static final String DATETIME_LINE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);

	public static String parseDateToString(Date date, String format) {
		if (date == null || format == null || "".equals(format)) {
			LOGGER.error("传入参数错误.");
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static Date format(Date date, String format) throws ParseException {
		if (date == null || format == null || "".equals(format)) {
			LOGGER.error("传入参数错误.");
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(sdf.format(date));
	}

	public static String parse(Date date, String format) {
		if (date == null || format == null || "".equals(format)) {
			LOGGER.error("传入参数错误.");
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static String format(String date, String format) throws ParseException {
		if (date == null || format == null || "".equals(format)) {
			LOGGER.error("传入参数错误.");
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(sdf.parse(date));
	}

	public static Date parse(String date, String format) throws ParseException {
		if (date == null || format == null || "".equals(format)) {
			LOGGER.error("传入参数错误.");
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(date);
	}
}
