/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.prolificinteractive.materialcalendarview;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarPagerView;
import com.prolificinteractive.materialcalendarview.DateRangeIndex;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.DecoratorResult;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.format.DayFormatter;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;
import com.prolificinteractive.materialcalendarview.format.WeekDayFormatter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

abstract class CalendarPagerAdapter<V extends CalendarPagerView>
extends PagerAdapter {
    private Integer color = null;
    private final ArrayDeque<V> currentViews;
    private Integer dateTextAppearance = null;
    private DayFormatter dayFormatter;
    private List<DecoratorResult> decoratorResults = null;
    private List<DayViewDecorator> decorators;
    private CalendarDay maxDate = null;
    protected final MaterialCalendarView mcv;
    private CalendarDay minDate = null;
    private DateRangeIndex rangeIndex;
    private List<CalendarDay> selectedDates = new ArrayList<CalendarDay>();
    private boolean selectionEnabled = true;
    private int showOtherDates = 4;
    private TitleFormatter titleFormatter = null;
    private final CalendarDay today;
    private WeekDayFormatter weekDayFormatter = WeekDayFormatter.DEFAULT;
    private Integer weekDayTextAppearance = null;

    CalendarPagerAdapter(MaterialCalendarView materialCalendarView) {
        this.dayFormatter = DayFormatter.DEFAULT;
        this.decorators = new ArrayList<DayViewDecorator>();
        this.mcv = materialCalendarView;
        this.today = CalendarDay.today();
        this.currentViews = new ArrayDeque();
        this.currentViews.iterator();
        this.setRangeDates(null, null);
    }

    private void invalidateSelectedDates() {
        this.validateSelectedDates();
        Iterator<V> iterator = this.currentViews.iterator();
        while (iterator.hasNext()) {
            ((CalendarPagerView)((Object)iterator.next())).setSelectedDates(this.selectedDates);
        }
    }

    private void validateSelectedDates() {
        int n = 0;
        while (n < this.selectedDates.size()) {
            int n2;
            block4: {
                CalendarDay calendarDay;
                block3: {
                    calendarDay = this.selectedDates.get(n);
                    if (this.minDate != null && this.minDate.isAfter(calendarDay)) break block3;
                    n2 = n;
                    if (this.maxDate == null) break block4;
                    n2 = n;
                    if (!this.maxDate.isBefore(calendarDay)) break block4;
                }
                this.selectedDates.remove(n);
                this.mcv.onDateUnselected(calendarDay);
                n2 = n - 1;
            }
            n = n2 + 1;
        }
    }

    public void clearSelections() {
        this.selectedDates.clear();
        this.invalidateSelectedDates();
    }

    protected abstract DateRangeIndex createRangeIndex(CalendarDay var1, CalendarDay var2);

    protected abstract V createView(int var1);

    @Override
    public void destroyItem(ViewGroup viewGroup, int n, Object object) {
        object = (CalendarPagerView)((Object)object);
        this.currentViews.remove(object);
        viewGroup.removeView((View)object);
    }

    @Override
    public int getCount() {
        return this.rangeIndex.getCount();
    }

    protected int getDateTextAppearance() {
        if (this.dateTextAppearance == null) {
            return 0;
        }
        return this.dateTextAppearance;
    }

    public int getIndexForDay(CalendarDay calendarDay) {
        if (calendarDay == null) {
            return this.getCount() / 2;
        }
        if (this.minDate != null && calendarDay.isBefore(this.minDate)) {
            return 0;
        }
        if (this.maxDate != null && calendarDay.isAfter(this.maxDate)) {
            return this.getCount() - 1;
        }
        return this.rangeIndex.indexOf(calendarDay);
    }

    public CalendarDay getItem(int n) {
        return this.rangeIndex.getItem(n);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public int getItemPosition(Object object) {
        int n;
        if (!this.isInstanceOfView(object)) {
            return -2;
        }
        if (((CalendarPagerView)((Object)object)).getFirstViewDay() == null) {
            return -2;
        }
        int n2 = n = this.indexOf((V)((Object)((CalendarPagerView)((Object)object))));
        if (n >= 0) return n2;
        return -2;
    }

    @Override
    public CharSequence getPageTitle(int n) {
        if (this.titleFormatter == null) {
            return "";
        }
        return this.titleFormatter.format(this.getItem(n));
    }

    public DateRangeIndex getRangeIndex() {
        return this.rangeIndex;
    }

    public List<CalendarDay> getSelectedDates() {
        return Collections.unmodifiableList(this.selectedDates);
    }

    public int getShowOtherDates() {
        return this.showOtherDates;
    }

    protected int getWeekDayTextAppearance() {
        if (this.weekDayTextAppearance == null) {
            return 0;
        }
        return this.weekDayTextAppearance;
    }

    protected abstract int indexOf(V var1);

    @Override
    public Object instantiateItem(ViewGroup viewGroup, int n) {
        V v = this.createView(n);
        v.setContentDescription(this.mcv.getCalendarContentDescription());
        v.setAlpha(0.0f);
        ((CalendarPagerView)((Object)v)).setSelectionEnabled(this.selectionEnabled);
        ((CalendarPagerView)((Object)v)).setWeekDayFormatter(this.weekDayFormatter);
        ((CalendarPagerView)((Object)v)).setDayFormatter(this.dayFormatter);
        if (this.color != null) {
            ((CalendarPagerView)((Object)v)).setSelectionColor(this.color);
        }
        if (this.dateTextAppearance != null) {
            ((CalendarPagerView)((Object)v)).setDateTextAppearance(this.dateTextAppearance);
        }
        if (this.weekDayTextAppearance != null) {
            ((CalendarPagerView)((Object)v)).setWeekDayTextAppearance(this.weekDayTextAppearance);
        }
        ((CalendarPagerView)((Object)v)).setShowOtherDates(this.showOtherDates);
        ((CalendarPagerView)((Object)v)).setMinimumDate(this.minDate);
        ((CalendarPagerView)((Object)v)).setMaximumDate(this.maxDate);
        ((CalendarPagerView)((Object)v)).setSelectedDates(this.selectedDates);
        viewGroup.addView(v);
        this.currentViews.add(v);
        ((CalendarPagerView)((Object)v)).setDayViewDecorators(this.decoratorResults);
        return v;
    }

    public void invalidateDecorators() {
        this.decoratorResults = new ArrayList<DecoratorResult>();
        for (DayViewDecorator dayViewDecorator : this.decorators) {
            DayViewFacade dayViewFacade = new DayViewFacade();
            dayViewDecorator.decorate(dayViewFacade);
            if (!dayViewFacade.isDecorated()) continue;
            this.decoratorResults.add(new DecoratorResult(dayViewDecorator, dayViewFacade));
        }
        Iterator<DayViewDecorator> iterator = this.currentViews.iterator();
        while (iterator.hasNext()) {
            ((CalendarPagerView)((Object)iterator.next())).setDayViewDecorators(this.decoratorResults);
        }
    }

    protected abstract boolean isInstanceOfView(Object var1);

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public CalendarPagerAdapter<?> migrateStateAndReturn(CalendarPagerAdapter<?> calendarPagerAdapter) {
        calendarPagerAdapter.titleFormatter = this.titleFormatter;
        calendarPagerAdapter.color = this.color;
        calendarPagerAdapter.dateTextAppearance = this.dateTextAppearance;
        calendarPagerAdapter.weekDayTextAppearance = this.weekDayTextAppearance;
        calendarPagerAdapter.showOtherDates = this.showOtherDates;
        calendarPagerAdapter.minDate = this.minDate;
        calendarPagerAdapter.maxDate = this.maxDate;
        calendarPagerAdapter.selectedDates = this.selectedDates;
        calendarPagerAdapter.weekDayFormatter = this.weekDayFormatter;
        calendarPagerAdapter.dayFormatter = this.dayFormatter;
        calendarPagerAdapter.decorators = this.decorators;
        calendarPagerAdapter.decoratorResults = this.decoratorResults;
        calendarPagerAdapter.selectionEnabled = this.selectionEnabled;
        return calendarPagerAdapter;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setDateSelected(CalendarDay calendarDay, boolean bl) {
        if (bl) {
            if (this.selectedDates.contains(calendarDay)) return;
            {
                this.selectedDates.add(calendarDay);
                this.invalidateSelectedDates();
                return;
            }
        } else {
            if (!this.selectedDates.contains(calendarDay)) return;
            {
                this.selectedDates.remove(calendarDay);
                this.invalidateSelectedDates();
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setDateTextAppearance(int n) {
        if (n != 0) {
            this.dateTextAppearance = n;
            Iterator<V> iterator = this.currentViews.iterator();
            while (iterator.hasNext()) {
                ((CalendarPagerView)((Object)iterator.next())).setDateTextAppearance(n);
            }
        }
    }

    public void setDayFormatter(DayFormatter dayFormatter) {
        this.dayFormatter = dayFormatter;
        Iterator<V> iterator = this.currentViews.iterator();
        while (iterator.hasNext()) {
            ((CalendarPagerView)((Object)iterator.next())).setDayFormatter(dayFormatter);
        }
    }

    public void setDecorators(List<DayViewDecorator> list) {
        this.decorators = list;
        this.invalidateDecorators();
    }

    public void setRangeDates(CalendarDay calendarDay, CalendarDay calendarDay2) {
        this.minDate = calendarDay;
        this.maxDate = calendarDay2;
        for (CalendarPagerView calendarPagerView : this.currentViews) {
            calendarPagerView.setMinimumDate(calendarDay);
            calendarPagerView.setMaximumDate(calendarDay2);
        }
        Object object = calendarDay;
        if (calendarDay == null) {
            object = CalendarDay.from(this.today.getYear() - 200, this.today.getMonth(), this.today.getDay());
        }
        calendarDay = calendarDay2;
        if (calendarDay2 == null) {
            calendarDay = CalendarDay.from(this.today.getYear() + 200, this.today.getMonth(), this.today.getDay());
        }
        this.rangeIndex = this.createRangeIndex((CalendarDay)object, calendarDay);
        this.notifyDataSetChanged();
        this.invalidateSelectedDates();
    }

    public void setSelectionColor(int n) {
        this.color = n;
        Iterator<V> iterator = this.currentViews.iterator();
        while (iterator.hasNext()) {
            ((CalendarPagerView)((Object)iterator.next())).setSelectionColor(n);
        }
    }

    public void setSelectionEnabled(boolean bl) {
        this.selectionEnabled = bl;
        Iterator<V> iterator = this.currentViews.iterator();
        while (iterator.hasNext()) {
            ((CalendarPagerView)((Object)iterator.next())).setSelectionEnabled(this.selectionEnabled);
        }
    }

    public void setShowOtherDates(int n) {
        this.showOtherDates = n;
        Iterator<V> iterator = this.currentViews.iterator();
        while (iterator.hasNext()) {
            ((CalendarPagerView)((Object)iterator.next())).setShowOtherDates(n);
        }
    }

    public void setTitleFormatter(TitleFormatter titleFormatter) {
        this.titleFormatter = titleFormatter;
    }

    public void setWeekDayFormatter(WeekDayFormatter weekDayFormatter) {
        this.weekDayFormatter = weekDayFormatter;
        Iterator<V> iterator = this.currentViews.iterator();
        while (iterator.hasNext()) {
            ((CalendarPagerView)((Object)iterator.next())).setWeekDayFormatter(weekDayFormatter);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setWeekDayTextAppearance(int n) {
        if (n != 0) {
            this.weekDayTextAppearance = n;
            Iterator<V> iterator = this.currentViews.iterator();
            while (iterator.hasNext()) {
                ((CalendarPagerView)((Object)iterator.next())).setWeekDayTextAppearance(n);
            }
        }
    }
}

