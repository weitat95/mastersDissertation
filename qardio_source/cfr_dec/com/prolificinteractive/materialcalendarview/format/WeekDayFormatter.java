/*
 * Decompiled with CFR 0.147.
 */
package com.prolificinteractive.materialcalendarview.format;

import com.prolificinteractive.materialcalendarview.CalendarUtils;
import com.prolificinteractive.materialcalendarview.format.CalendarWeekDayFormatter;
import java.util.Calendar;

public interface WeekDayFormatter {
    public static final WeekDayFormatter DEFAULT = new CalendarWeekDayFormatter(CalendarUtils.getInstance());

    public CharSequence format(int var1);
}

