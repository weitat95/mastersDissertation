/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 */
package com.prolificinteractive.materialcalendarview;

import android.annotation.SuppressLint;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarPagerView;
import com.prolificinteractive.materialcalendarview.DayView;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import java.util.Calendar;
import java.util.Collection;

@SuppressLint(value={"ViewConstructor"})
class MonthView
extends CalendarPagerView {
    public MonthView(MaterialCalendarView materialCalendarView, CalendarDay calendarDay, int n) {
        super(materialCalendarView, calendarDay, n);
    }

    @Override
    protected void buildDayViews(Collection<DayView> collection, Calendar calendar) {
        for (int i = 0; i < 6; ++i) {
            for (int j = 0; j < 7; ++j) {
                this.addDayView(collection, calendar);
            }
        }
    }

    public CalendarDay getMonth() {
        return this.getFirstViewDay();
    }

    @Override
    protected int getRows() {
        return 7;
    }

    @Override
    protected boolean isDayEnabled(CalendarDay calendarDay) {
        return calendarDay.getMonth() == this.getFirstViewDay().getMonth();
    }
}

