package org.example.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);

    public static void setFormat(String format) {
        simpleDateFormat = new SimpleDateFormat(format);
    }

    public static Date parseDate(String date) throws ParseException {
        return simpleDateFormat.parse(date);
    }

}
