package com.swordfish.utils.common;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {

    /**
     * @param utcTimeStr: "2024-09-07T12:30:00Z"
     */
    public static Date convertToUTCDate(String utcTimeStr) {
        Instant instant = Instant.parse(utcTimeStr);
        return Date.from(instant);
    }

    public static String convertToUTCStr(Date date) {
        Instant instant = date.toInstant();
        ZonedDateTime utcTime = instant.atZone(ZoneOffset.UTC);
        return utcTime.format(DateTimeFormatter.ISO_INSTANT);
    }

    public static Date nowUTC() {
        return Date.from(Instant.now());
    }

}
