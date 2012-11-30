package com.hoiio.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public final class DateUtil {

    private static final SimpleDateFormat WEB_API_DATE_FORMAT = new SimpleDateFormat("ddMMyy");
    private static final String DATE_TIME_TIMEZONE_FORMAT_STR = "yyyy-MM-dd HH:mm:ss Z";
    private static final String DATE_TIME_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_FORMAT_STR = "yyyy-MM-dd";

    public static String convertDateTimeToString(Date date) {
        return convertDateToString(date, DATE_TIME_FORMAT_STR);
    }

    public static String convertDateOnlyToString(Date date) {
        return convertDateToString(date, DATE_FORMAT_STR);
    }

    public static String convertDateToString(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    public static Date convertStringToDateTime(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT_STR);
        sdf.setLenient(false);
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            return null; // represent conversion failed.
        }
    }

    public static Date convertStringToDate(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_STR);
        sdf.setLenient(false);
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            return null; // represent conversion failed.
        }
    }

    public static boolean isSameSecond(Date date1, Date date2) {
        // getTime will return epoch in milliseconds.
        // trim the millisecond data by dividing by 1000.
        return ((date1.getTime() / 1000) == (date2.getTime() / 1000));
    }

    public static Date getDateFromDDMMYY(String dateCode) {
        try {
            return WEB_API_DATE_FORMAT.parse(dateCode);
        } catch (ParseException ex) {
            return null;
        }
    }

    public static String convertToDateTimeString(Date date, String gmt) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_TIMEZONE_FORMAT_STR);
        sdf.setTimeZone(TimeZone.getTimeZone(gmt.trim()));
        return sdf.format(date);
    }

    public static DateFormat format() {
        return new SimpleDateFormat(DATE_TIME_FORMAT_STR);
    }
}
