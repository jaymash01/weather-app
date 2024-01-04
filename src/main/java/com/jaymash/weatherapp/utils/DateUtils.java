package com.jaymash.weatherapp.utils;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {

    public static String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        dateFormat.setTimeZone(TimeZone.getDefault());
        return dateFormat.format(date);
    }

    public static String formatDate(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
        dateFormat.setTimeZone(TimeZone.getDefault());
        return dateFormat.format(date);
    }

    public static LocalDateTime utcTimestampToLocalDateTime(long timestampInMillis) {
        Instant instant = Instant.ofEpochMilli(timestampInMillis);
        return LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));
    }

    public static String getDayNameFromAddedDays(Date startDate, int dayOffset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate == null ? new Date() : startDate);
        calendar.add(Calendar.DAY_OF_MONTH, dayOffset);

        Date date = calendar.getTime();
        return new SimpleDateFormat("EEEEE").format(date);
    }

}
