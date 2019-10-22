/*
 * Decompiled with CFR 0.147.
 */
package com.prolificinteractive.materialcalendarview;

import java.util.Calendar;
import java.util.Date;

public class CalendarUtils {
    public static void copyDateTo(Calendar calendar, Calendar calendar2) {
        int n = CalendarUtils.getYear(calendar);
        int n2 = CalendarUtils.getMonth(calendar);
        int n3 = CalendarUtils.getDay(calendar);
        calendar2.clear();
        calendar2.set(n, n2, n3);
    }

    public static int getDay(Calendar calendar) {
        return calendar.get(5);
    }

    public static int getDayOfWeek(Calendar calendar) {
        return calendar.get(7);
    }

    public static Calendar getInstance() {
        Calendar calendar = Calendar.getInstance();
        CalendarUtils.copyDateTo(calendar, calendar);
        return calendar;
    }

    public static Calendar getInstance(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        CalendarUtils.copyDateTo(calendar, calendar);
        return calendar;
    }

    public static int getMonth(Calendar calendar) {
        return calendar.get(2);
    }

    public static int getYear(Calendar calendar) {
        return calendar.get(1);
    }
}

