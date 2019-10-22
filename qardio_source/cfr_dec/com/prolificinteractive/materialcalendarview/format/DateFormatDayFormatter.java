/*
 * Decompiled with CFR 0.147.
 */
package com.prolificinteractive.materialcalendarview.format;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.format.DayFormatter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatDayFormatter
implements DayFormatter {
    private final DateFormat dateFormat = new SimpleDateFormat("d", Locale.getDefault());

    @Override
    public String format(CalendarDay calendarDay) {
        return this.dateFormat.format(calendarDay.getDate());
    }
}

