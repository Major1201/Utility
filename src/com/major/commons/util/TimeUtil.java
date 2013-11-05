package com.major.commons.util;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * User: Minjie
 * Date: 13-9-1
 * Time: 下午4:58
 */
@SuppressWarnings("UnusedDeclaration")
public class TimeUtil {
    /**
     * Don't let anyone instantiate this class.
     */
    private TimeUtil() {}

    private static final String DATE_FORMAT_SHORT = "yyyyMMdd";
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    private static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    private static final String DEFAULT_FULL_DATE_FORMAT = DEFAULT_DATE_FORMAT + " " + DEFAULT_TIME_FORMAT;
    private static final String DEFAULT_FULL_DATE_WITH_MILLIS_FORMAT = DEFAULT_FULL_DATE_FORMAT + ".SSS";

    /**
     * Get current date
     * @return Date
     */
    public static Date now() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * Get system time.
     * @return String
     */
    public static String getSystemDate() {
        return formatDate(now(), DEFAULT_DATE_FORMAT);
    }
    public static String getSystemTime() {
        return formatDate(now(), DEFAULT_TIME_FORMAT);
    }
    public static String getSystemDateFull() {
        return formatDate(now(), DEFAULT_FULL_DATE_FORMAT);
    }
    //e.g. 20130901
    public static Long getSystemDateInLong() {
        return Long.valueOf(formatDate(now(), DATE_FORMAT_SHORT));
    }

    /**
     * default format:"yyyy-MM-dd"
     * @param date date to format
     * @param format format
     * @return String
     */
    public static String formatDate(Date date, String format) {
        if (date == null)
            throw new IllegalArgumentException("null date");

        if (StringUtils.isEmpty(format))
            format = DEFAULT_DATE_FORMAT;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Convert a String to a Date
     * @param date String to convert
     * @param format format
     * @return Date
     */
    public static Date convertStringToDate(String date, String format) {
        if (StringUtils.isEmpty(date))
            throw new IllegalArgumentException("date is empty.");

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Convert String to Date Error,check String format.");
        }
    }
    /**
     * A simple version of convertStringToDate.
     * Only allow "yy-MM-dd", "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss.SSS"
     * @param date String to convert
     * @return Date
     */
    public static Date convertStringToDate(String date) {
        if (StringUtils.isEmpty(date))
            throw new IllegalArgumentException("date is empty.");

        switch (date.length()) {
            case 8: return convertStringToDate(date, DATE_FORMAT_SHORT);
            case 10: return convertStringToDate(date, DEFAULT_DATE_FORMAT);
            case 19: return convertStringToDate(date, DEFAULT_FULL_DATE_FORMAT);
            case 23: return convertStringToDate(date, DEFAULT_FULL_DATE_WITH_MILLIS_FORMAT);
            default: throw new IllegalArgumentException
                    ("Please use convertStringToDate(String date, String format) if the 'date' is not common.");
        }
    }

    /**
     *
     * @param date date to get
     * @param type 0: e.g. Sunday
     *              1: e.g. Sun
     *              2: e.g. 周日
     *              3：e.g. 星期日
     * @return String
     */
    public static String getWeek(Date date, int type) {
        if (date == null)
            throw new IllegalArgumentException("null date");

        final String weekDayName[][] =
                {{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"},
                {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"},
                {"周日", "周一", "周二", "周三", "周四", "周五", "周六"},
                {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"}};

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return weekDayName[type][dayOfWeek - 1];
    }

    /**
     * endDate - beginDate
     * @param beginDate begin date
     * @param endDate end date
     * @return days between beginDate and endDate, ignore hour, minutes and seconds.
     */
    public static long getBetweenDays(Date beginDate, Date endDate) {
        Date bgn = convertStringToDate(formatDate(beginDate, DEFAULT_DATE_FORMAT));
        Date end = convertStringToDate(formatDate(endDate, DEFAULT_DATE_FORMAT));
        long betweenDays = end.getTime() - bgn.getTime();
        betweenDays = betweenDays / (1000 * 60 * 60 * 24);
        return betweenDays;
    }

    /**
     * convert a date to Chinese pattern
     * @param date date to convert(it must between 1900-01-31 to 2049-12-31).
     * @return String
     */
    public static Lunar toChineseDate(Date date) {
        return new Lunar(date);
    }
}
