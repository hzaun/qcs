package com.nuzharukiya.spapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Nuzha Rukiya on 18/06/20.
 */
public class TimeUtils {

    private static final SimpleDateFormat SDF_24_HOURS = new SimpleDateFormat("HH:mm");
    private static final SimpleDateFormat SDF_12_HOURS = new SimpleDateFormat("hh:mm a");
    private static final SimpleDateFormat SDF_DATE_YYMMDD = new SimpleDateFormat("yyMMdd");
    private static final SimpleDateFormat SDF_DATE_PRETTY = new SimpleDateFormat("dd MMM, YYYY");

    public static String convert24To12(String timeIn24) {
        try {
            return SDF_12_HOURS.format(SDF_24_HOURS.parse(timeIn24));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String prettyDate(String dateInYYMMDD) {
        try {
            return SDF_DATE_PRETTY.format(SDF_DATE_YYMMDD.parse(dateInYYMMDD));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static long convert24ToMillis(String timeIn24){
        try {
            return SDF_24_HOURS.parse(timeIn24).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
