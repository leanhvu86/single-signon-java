package com.tsolution.sso.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DatetimeUtils {
	private DatetimeUtils() {
	}

	public static java.util.Date localDateTime2UtilDate(LocalDateTime localDateTime) {
		return DatetimeUtils.convertLocalDateTimeToDate(localDateTime);
	}

	public static java.sql.Date localDateTime2SqlDate(LocalDateTime localDateTime) {
		if (localDateTime == null) {
			return null;
		}
		java.util.Date date = DatetimeUtils.convertLocalDateTimeToDate(localDateTime);
		return new java.sql.Date(date == null ? null : date.getTime());
	}

	private static java.util.Date convertLocalDateTimeToDate(LocalDateTime localDateTime) {
		if (localDateTime == null) {
			return null;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_PATTERN);
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Constants.DATE_PATTERN);
		java.util.Date utilDate = null;
		try {
			utilDate = simpleDateFormat.parse(localDateTime.format(dateTimeFormatter));
		} catch (ParseException e) {
			utilDate = null;
		}
		return utilDate;
	}

	public static LocalDateTime timeStamp2LocalDateTime(java.sql.Timestamp timeStamp) {
		if (timeStamp == null) {
			return null;
		}
		return timeStamp.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}
}
