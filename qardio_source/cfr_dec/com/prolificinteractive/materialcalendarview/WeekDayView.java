/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.widget.TextView
 */
package com.prolificinteractive.materialcalendarview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.widget.TextView;
import com.prolificinteractive.materialcalendarview.format.WeekDayFormatter;

@SuppressLint(value={"ViewConstructor"})
class WeekDayView
extends TextView {
    private int dayOfWeek;
    private WeekDayFormatter formatter = WeekDayFormatter.DEFAULT;

    public WeekDayView(Context context, int n) {
        super(context);
        this.setGravity(17);
        if (Build.VERSION.SDK_INT >= 17) {
            this.setTextAlignment(4);
        }
        this.setDayOfWeek(n);
    }

    public void setDayOfWeek(int n) {
        this.dayOfWeek = n;
        this.setText(this.formatter.format(n));
    }

    public void setWeekDayFormatter(WeekDayFormatter weekDayFormatter) {
        WeekDayFormatter weekDayFormatter2 = weekDayFormatter;
        if (weekDayFormatter == null) {
            weekDayFormatter2 = WeekDayFormatter.DEFAULT;
        }
        this.formatter = weekDayFormatter2;
        this.setDayOfWeek(this.dayOfWeek);
    }
}

