package com.example.todolist.helper;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public final class Helper {
    public static String convertDateTimeObjectToIsoString(ZonedDateTime dateTime){
        if (dateTime == null) {
            throw new IllegalArgumentException("ZoneDateTime cannot be null");
        }

        return dateTime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }

    public static boolean hasTimeZone(String isostirng) {
        if (isostirng == null) {
            return false;
        }

        return isostirng.matches(".*(Z|[+-]\\d{2}:\\d{2})$");
    }
    public static ZonedDateTime convertISoStringToZoneDateTime(String isostring){
        String timezoneString = hasTimeZone(isostring) ? isostring : isostring + "Z";
        return ZonedDateTime.parse(timezoneString, DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }
}
