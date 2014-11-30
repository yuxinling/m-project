package com.zcloud.logger.clean.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * User: yuyangning
 * Date: 5/29/14
 * Time: 6:34 PM
 */
public class CommonUtils {
    public static String dateString(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static String dateString(Date date) {
        return dateString(date, "yyyy.MM.dd");
    }

    public static String dateString(int before) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, -before);

        return dateString(calendar.getTime());
    }

    public static Date date(String date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date date(String date) {
        return date(date, "yyyy.MM.dd");
    }

    public static boolean isIn(int reserveDayCount, String dateString) {
        Date date = date(dateString);
        int interval = interval(date, new Date());
        if (interval < 0) {
            return reserveDayCount > Math.abs(interval);
        }
        return false;
    }

    public static boolean isNotIn(int reserveDayCount, String dateString) {
        Date date = date(dateString);
        int interval = interval(date, new Date());
        if (interval < 0) {
            return reserveDayCount <= Math.abs(interval);
        }
        return false;
    }

    public static int interval(Date dateOne, Date dateTwo) {
        Calendar calOne = Calendar.getInstance();
        calOne.setTime(dateOne);
        int dayOne = calOne.get(Calendar.DAY_OF_YEAR);

        Calendar calTwo = Calendar.getInstance();
        calTwo.setTime(dateTwo);
        int dayTwo = calTwo.get(Calendar.DAY_OF_YEAR);
        return dayOne - dayTwo;
    }
}
