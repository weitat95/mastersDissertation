/*
 * Decompiled with CFR 0.147.
 */
package com.prolificinteractive.materialcalendarview.format;

import com.prolificinteractive.materialcalendarview.format.WeekDayFormatter;

public class ArrayWeekDayFormatter
implements WeekDayFormatter {
    private final CharSequence[] weekDayLabels;

    public ArrayWeekDayFormatter(CharSequence[] arrcharSequence) {
        if (arrcharSequence == null) {
            throw new IllegalArgumentException("Cannot be null");
        }
        if (arrcharSequence.length != 7) {
            throw new IllegalArgumentException("Array must contain exactly 7 elements");
        }
        this.weekDayLabels = arrcharSequence;
    }

    @Override
    public CharSequence format(int n) {
        return this.weekDayLabels[n - 1];
    }
}

