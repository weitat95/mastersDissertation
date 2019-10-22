/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.text.SpannableStringBuilder
 */
package com.prolificinteractive.materialcalendarview.format;

import android.text.SpannableStringBuilder;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;

public class MonthArrayTitleFormatter
implements TitleFormatter {
    private final CharSequence[] monthLabels;

    public MonthArrayTitleFormatter(CharSequence[] arrcharSequence) {
        if (arrcharSequence == null) {
            throw new IllegalArgumentException("Label array cannot be null");
        }
        if (arrcharSequence.length < 12) {
            throw new IllegalArgumentException("Label array is too short");
        }
        this.monthLabels = arrcharSequence;
    }

    @Override
    public CharSequence format(CalendarDay calendarDay) {
        return new SpannableStringBuilder().append(this.monthLabels[calendarDay.getMonth()]).append((CharSequence)" ").append((CharSequence)String.valueOf(calendarDay.getYear()));
    }
}

