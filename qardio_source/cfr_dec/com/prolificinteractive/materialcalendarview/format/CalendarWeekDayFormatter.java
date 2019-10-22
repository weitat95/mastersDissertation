/*
 * Decompiled with CFR 0.147.
 */
package com.prolificinteractive.materialcalendarview.format;

import com.prolificinteractive.materialcalendarview.CalendarUtils;
import com.prolificinteractive.materialcalendarview.format.WeekDayFormatter;
import java.util.Calendar;
import java.util.Locale;

public class CalendarWeekDayFormatter
implements WeekDayFormatter {
    private final Calendar calendar;

    public CalendarWeekDayFormatter() {
        this(CalendarUtils.getInstance());
    }

    public CalendarWeekDayFormatter(Calendar calendar) {
        calendar.get(7);
        this.calendar = calendar;
    }

    @Override
    public CharSequence format(int n) {
        this.calendar.set(7, n);
        return this.calendar.getDisplayName(7, 1, Locale.getDefault());
    }
}

