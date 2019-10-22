/*
 * Decompiled with CFR 0.147.
 */
package com.segment.analytics.internal;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

final class Iso8601Utils {
    static final TimeZone TIMEZONE_Z = TimeZone.getTimeZone("GMT");

    public static String format(Date serializable) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(TIMEZONE_Z, Locale.US);
        gregorianCalendar.setTime((Date)serializable);
        serializable = new StringBuilder("yyyy-MM-ddThh:mm:ss.sssZ".length());
        Iso8601Utils.padInt((StringBuilder)serializable, gregorianCalendar.get(1), "yyyy".length());
        ((StringBuilder)serializable).append('-');
        Iso8601Utils.padInt((StringBuilder)serializable, gregorianCalendar.get(2) + 1, "MM".length());
        ((StringBuilder)serializable).append('-');
        Iso8601Utils.padInt((StringBuilder)serializable, gregorianCalendar.get(5), "dd".length());
        ((StringBuilder)serializable).append('T');
        Iso8601Utils.padInt((StringBuilder)serializable, gregorianCalendar.get(11), "hh".length());
        ((StringBuilder)serializable).append(':');
        Iso8601Utils.padInt((StringBuilder)serializable, gregorianCalendar.get(12), "mm".length());
        ((StringBuilder)serializable).append(':');
        Iso8601Utils.padInt((StringBuilder)serializable, gregorianCalendar.get(13), "ss".length());
        ((StringBuilder)serializable).append('.');
        Iso8601Utils.padInt((StringBuilder)serializable, gregorianCalendar.get(14), "sss".length());
        ((StringBuilder)serializable).append('Z');
        return ((StringBuilder)serializable).toString();
    }

    private static void padInt(StringBuilder stringBuilder, int n, int n2) {
        String string2 = Integer.toString(n);
        for (n = n2 - string2.length(); n > 0; --n) {
            stringBuilder.append('0');
        }
        stringBuilder.append(string2);
    }
}

