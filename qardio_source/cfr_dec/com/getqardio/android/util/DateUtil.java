/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {
    private static int absoluteDay(Calendar calendar) {
        return calendar.get(6) + DateUtil.daysInPriorYears(calendar.get(1));
    }

    private static Calendar dayStart(Calendar calendar) {
        calendar.get(11);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        calendar.get(11);
        return calendar;
    }

    private static int daysInPriorYears(int n) {
        int n2 = n - 1;
        return (n - 1900) * 365 + (n2 / 4 - n2 / 100 + n2 / 400 - 460);
    }

    public static int getDaysForDate(Date date) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        return DateUtil.internalGetDaysForDate(gregorianCalendar);
    }

    private static int internalGetDaysForDate(Calendar calendar) {
        double d;
        double d2 = d = (double)(((calendar.get(11) * 60 + calendar.get(12)) * 60 + calendar.get(13)) * 1000 + calendar.get(14)) / 8.64E7 + (double)DateUtil.absoluteDay(DateUtil.dayStart(calendar));
        if (d >= 60.0) {
            d2 = d + 1.0;
        }
        return (int)Math.round(d2);
    }
}

