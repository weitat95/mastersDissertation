/*
 * Decompiled with CFR 0.147.
 */
package com.prolificinteractive.materialcalendarview.format;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.format.DateFormatDayFormatter;

public interface DayFormatter {
    public static final DayFormatter DEFAULT = new DateFormatDayFormatter();

    public String format(CalendarDay var1);
}

