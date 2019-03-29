package com.moon.enums;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 * @author benshaoye
 */
public enum DateFormatEnum {
    DEFAULT,
    /**
     * Asia/Shanghai
     */
    ASIA_SHANGHAI,
    /**
     * Africa/Cairo
     */
    AFRICA_CAIRO,
    /**
     * America/St_Johns
     */
    AMERICA_ST$JOHNS,
    /**
     * America/Puerto_Rico
     */
    AMERICA_PUERTO$RICO,
    /**
     * America/Phoenix
     */
    AMERICA_PHOENIX,
    /**
     * Asia/Karachi
     */
    ASIA_KARACHI,
    /**
     * America/Anchorage
     */
    AMERICA_ANCHORAGE,
    /**
     * Asia/Dhaka
     */
    ASIA_DHAKA,
    /**
     * America/Chicago
     */
    AMERICA_CHICAGO,
    /**
     * Asia/Tokyo
     */
    ASIA_TOKYO,
    /**
     * Asia/Kolkata
     */
    ASIA_KOLKATA,
    /**
     * America/Argentina/Buenos_Aires
     */
    AMERICA_ARGENTINA_BUENOS$AIRES,
    /**
     * Pacific/Auckland
     */
    PACIFIC_AUCKLAND,
    /**
     * Australia/Sydney
     */
    AUSTRALIA_SYDNEY,
    /**
     * America/Sao_Paulo
     */
    AMERICA_SAO$PAULO,
    /**
     * America/Los_Angeles
     */
    AMERICA_LOS$ANGELES,
    /**
     * Australia/Darwin
     */
    AUSTRALIA_DARWIN,
    /**
     * Pacific/Guadalcanal
     */
    PACIFIC_GUADALCANAL,
    /**
     * Asia/Ho_Chi_Minh
     */
    ASIA_HO$CHI$MINH,
    /**
     * Africa/Harare
     */
    AFRICA_HARARE,
    /**
     * Europe/Paris
     */
    EUROPE_PARIS,
    /**
     * Africa/Addis_Ababa
     */
    AFRICA_ADDIS$ABABA,
    /**
     * America/Indiana/Indianapolis
     */
    AMERICA_INDIANA_INDIANAPOLIS,
    /**
     * Pacific/Apia
     */
    PACIFIC_APIA,
    /**
     * Asia/Yerevan
     */
    ASIA_YEREVAN,
    ;

    public DateFormat of(String pattern) {
        return with(pattern);
    }

    public DateFormat with(String pattern) {
        DateFormat format = getDefault(pattern);
        if (this != DEFAULT) {
            format.setTimeZone(TimeZone.getTimeZone(formatName()));
        }
        return format;
    }

    public final static DateFormat getDefault(String pattern) {
        return new SimpleDateFormat(pattern);
    }

    public final static DateTimeFormatter getDefaultFormatter(String pattern) {
        return DateTimeFormatter.ofPattern(pattern);
    }

    private final String formatName() {
        char[] chars = name().toCharArray();
        boolean isBegin = true;
        char ch;
        for (int i = 0, len = chars.length; i < len; i++) {
            ch = chars[i];
            if (i == 0 || isBegin) {
                chars[i] = Character.toUpperCase(ch);
                isBegin = false;
            } else if (ch == '_') {
                isBegin = true;
                chars[i] = '/';
            } else if (ch == '$') {
                isBegin = true;
                chars[i] = '_';
            } else {
                chars[i] = Character.toLowerCase(ch);
            }
        }
        return new String(chars);
    }
}
