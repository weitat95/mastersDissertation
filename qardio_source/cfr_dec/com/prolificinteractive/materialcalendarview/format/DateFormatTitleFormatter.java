/*
 * Decompiled with CFR 0.147.
 */
package com.prolificinteractive.materialcalendarview.format;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatTitleFormatter
implements TitleFormatter {
    private final DateFormat dateFormat = new SimpleDateFormat("LLLL yyyy", Locale.getDefault());

    @Override
    public CharSequence format(CalendarDay calendarDay) {
        return this.dateFormat.format(calendarDay.getDate());
    }
}

