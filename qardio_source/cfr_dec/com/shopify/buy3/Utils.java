/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.buy3;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

final class Utils {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZ");

    public static String checkNotBlank(String string2, Object object) {
        if (string2 == null) {
            throw new NullPointerException(String.valueOf(object));
        }
        if (string2.isEmpty()) {
            throw new IllegalArgumentException(String.valueOf(object));
        }
        return string2;
    }

    static <T> T checkNotNull(T t, Object object) {
        if (t == null) {
            throw new NullPointerException(String.valueOf(object));
        }
        return t;
    }

    static DateTime parseDateTime(String string2) {
        return DateTime.parse(string2, DATE_TIME_FORMATTER);
    }
}

