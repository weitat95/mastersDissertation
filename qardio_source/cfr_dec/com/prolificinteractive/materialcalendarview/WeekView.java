/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityNodeInfo
 */
package com.prolificinteractive.materialcalendarview;

import android.annotation.SuppressLint;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarPagerView;
import com.prolificinteractive.materialcalendarview.DayView;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.format.DayFormatter;
import com.prolificinteractive.materialcalendarview.format.WeekDayFormatter;
import java.util.Calendar;
import java.util.Collection;

@SuppressLint(value={"ViewConstructor"})
public class WeekView
extends CalendarPagerView {
    public WeekView(MaterialCalendarView materialCalendarView, CalendarDay calendarDay, int n) {
        super(materialCalendarView, calendarDay, n);
    }

    @Override
    protected void buildDayViews(Collection<DayView> collection, Calendar calendar) {
        for (int i = 0; i < 7; ++i) {
            this.addDayView(collection, calendar);
        }
    }

    @Override
    protected int getRows() {
        return 2;
    }

    @Override
    protected boolean isDayEnabled(CalendarDay calendarDay) {
        return true;
    }
}

