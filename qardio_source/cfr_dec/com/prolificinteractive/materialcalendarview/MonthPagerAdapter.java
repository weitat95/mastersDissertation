/*
 * Decompiled with CFR 0.147.
 */
package com.prolificinteractive.materialcalendarview;

import android.support.v4.util.SparseArrayCompat;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarPagerAdapter;
import com.prolificinteractive.materialcalendarview.CalendarPagerView;
import com.prolificinteractive.materialcalendarview.DateRangeIndex;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.MonthView;

class MonthPagerAdapter
extends CalendarPagerAdapter<MonthView> {
    MonthPagerAdapter(MaterialCalendarView materialCalendarView) {
        super(materialCalendarView);
    }

    @Override
    protected DateRangeIndex createRangeIndex(CalendarDay calendarDay, CalendarDay calendarDay2) {
        return new Monthly(calendarDay, calendarDay2);
    }

    @Override
    protected MonthView createView(int n) {
        return new MonthView(this.mcv, this.getItem(n), this.mcv.getFirstDayOfWeek());
    }

    @Override
    protected int indexOf(MonthView object) {
        object = object.getMonth();
        return this.getRangeIndex().indexOf((CalendarDay)object);
    }

    @Override
    protected boolean isInstanceOfView(Object object) {
        return object instanceof MonthView;
    }

    public static class Monthly
    implements DateRangeIndex {
        private final int count;
        private SparseArrayCompat<CalendarDay> dayCache = new SparseArrayCompat();
        private final CalendarDay min;

        public Monthly(CalendarDay calendarDay, CalendarDay calendarDay2) {
            this.min = CalendarDay.from(calendarDay.getYear(), calendarDay.getMonth(), 1);
            this.count = this.indexOf(CalendarDay.from(calendarDay2.getYear(), calendarDay2.getMonth(), 1)) + 1;
        }

        @Override
        public int getCount() {
            return this.count;
        }

        @Override
        public CalendarDay getItem(int n) {
            int n2;
            CalendarDay calendarDay = this.dayCache.get(n);
            if (calendarDay != null) {
                return calendarDay;
            }
            int n3 = n / 12;
            int n4 = this.min.getYear() + n3;
            int n5 = n2 = this.min.getMonth() + n % 12;
            n3 = n4;
            if (n2 >= 12) {
                n3 = n4 + 1;
                n5 = n2 - 12;
            }
            calendarDay = CalendarDay.from(n3, n5, 1);
            this.dayCache.put(n, calendarDay);
            return calendarDay;
        }

        @Override
        public int indexOf(CalendarDay calendarDay) {
            return (calendarDay.getYear() - this.min.getYear()) * 12 + (calendarDay.getMonth() - this.min.getMonth());
        }
    }

}

