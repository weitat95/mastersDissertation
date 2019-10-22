/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.prolificinteractive.materialcalendarview;

import android.view.View;
import android.view.ViewGroup;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarPagerAdapter;
import com.prolificinteractive.materialcalendarview.CalendarPagerView;
import com.prolificinteractive.materialcalendarview.DateRangeIndex;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.WeekView;
import com.prolificinteractive.materialcalendarview.format.DayFormatter;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;
import com.prolificinteractive.materialcalendarview.format.WeekDayFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WeekPagerAdapter
extends CalendarPagerAdapter<WeekView> {
    public WeekPagerAdapter(MaterialCalendarView materialCalendarView) {
        super(materialCalendarView);
    }

    @Override
    protected DateRangeIndex createRangeIndex(CalendarDay calendarDay, CalendarDay calendarDay2) {
        return new Weekly(calendarDay, calendarDay2, this.mcv.getFirstDayOfWeek());
    }

    @Override
    protected WeekView createView(int n) {
        return new WeekView(this.mcv, this.getItem(n), this.mcv.getFirstDayOfWeek());
    }

    @Override
    protected int indexOf(WeekView object) {
        object = object.getFirstViewDay();
        return this.getRangeIndex().indexOf((CalendarDay)object);
    }

    @Override
    protected boolean isInstanceOfView(Object object) {
        return object instanceof WeekView;
    }

    public static class Weekly
    implements DateRangeIndex {
        private final int count;
        private final CalendarDay min;

        public Weekly(CalendarDay calendarDay, CalendarDay calendarDay2, int n) {
            this.min = this.getFirstDayOfWeek(calendarDay, n);
            this.count = this.weekNumberDifference(this.min, calendarDay2) + 1;
        }

        private CalendarDay getFirstDayOfWeek(CalendarDay calendarDay, int n) {
            Calendar calendar = Calendar.getInstance();
            calendarDay.copyTo(calendar);
            while (calendar.get(7) != n) {
                calendar.add(7, -1);
            }
            return CalendarDay.from(calendar);
        }

        private int weekNumberDifference(CalendarDay calendarDay, CalendarDay calendarDay2) {
            long l = calendarDay2.getDate().getTime();
            long l2 = calendarDay.getDate().getTime();
            int n = calendarDay2.getCalendar().get(16);
            int n2 = calendarDay.getCalendar().get(16);
            return (int)(TimeUnit.DAYS.convert((long)n + (l - l2) - (long)n2, TimeUnit.MILLISECONDS) / 7L);
        }

        @Override
        public int getCount() {
            return this.count;
        }

        @Override
        public CalendarDay getItem(int n) {
            return CalendarDay.from(new Date(this.min.getDate().getTime() + TimeUnit.MILLISECONDS.convert(n * 7, TimeUnit.DAYS)));
        }

        @Override
        public int indexOf(CalendarDay calendarDay) {
            return this.weekNumberDifference(this.min, calendarDay);
        }
    }

}

