/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityNodeInfo
 */
package com.prolificinteractive.materialcalendarview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarUtils;
import com.prolificinteractive.materialcalendarview.DayView;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.DecoratorResult;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.WeekDayView;
import com.prolificinteractive.materialcalendarview.format.DayFormatter;
import com.prolificinteractive.materialcalendarview.format.WeekDayFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

abstract class CalendarPagerView
extends ViewGroup
implements View.OnClickListener {
    private static final Calendar tempWorkingCalendar = CalendarUtils.getInstance();
    private final Collection<DayView> dayViews;
    private final ArrayList<DecoratorResult> decoratorResults;
    private int firstDayOfWeek;
    private CalendarDay firstViewDay;
    private CalendarDay maxDate = null;
    private MaterialCalendarView mcv;
    private CalendarDay minDate = null;
    protected int showOtherDates = 4;
    private final ArrayList<WeekDayView> weekDayViews = new ArrayList();

    public CalendarPagerView(MaterialCalendarView materialCalendarView, CalendarDay calendarDay, int n) {
        super(materialCalendarView.getContext());
        this.decoratorResults = new ArrayList();
        this.dayViews = new ArrayList<DayView>();
        this.mcv = materialCalendarView;
        this.firstViewDay = calendarDay;
        this.firstDayOfWeek = n;
        this.setClipChildren(false);
        this.setClipToPadding(false);
        this.buildWeekDays(this.resetAndGetWorkingCalendar());
        this.buildDayViews(this.dayViews, this.resetAndGetWorkingCalendar());
    }

    private void buildWeekDays(Calendar calendar) {
        for (int i = 0; i < 7; ++i) {
            WeekDayView weekDayView = new WeekDayView(this.getContext(), CalendarUtils.getDayOfWeek(calendar));
            this.weekDayViews.add(weekDayView);
            this.addView((View)weekDayView);
            calendar.add(5, 1);
        }
    }

    protected void addDayView(Collection<DayView> collection, Calendar calendar) {
        Object object = CalendarDay.from(calendar);
        object = new DayView(this.getContext(), (CalendarDay)object);
        object.setOnClickListener((View.OnClickListener)this);
        collection.add((DayView)((Object)object));
        this.addView((View)object, (ViewGroup.LayoutParams)new LayoutParams());
        calendar.add(5, 1);
    }

    protected abstract void buildDayViews(Collection<DayView> var1, Calendar var2);

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams();
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams();
    }

    protected int getFirstDayOfWeek() {
        return this.firstDayOfWeek;
    }

    protected CalendarDay getFirstViewDay() {
        return this.firstViewDay;
    }

    protected abstract int getRows();

    protected void invalidateDecorators() {
        DayViewFacade dayViewFacade = new DayViewFacade();
        for (DayView dayView : this.dayViews) {
            dayViewFacade.reset();
            for (DecoratorResult decoratorResult : this.decoratorResults) {
                if (!decoratorResult.decorator.shouldDecorate(dayView.getDate())) continue;
                decoratorResult.result.applyTo(dayViewFacade);
            }
            dayView.applyFacade(dayViewFacade);
        }
    }

    protected abstract boolean isDayEnabled(CalendarDay var1);

    public void onClick(View object) {
        if (object instanceof DayView) {
            object = (DayView)((Object)object);
            this.mcv.onDateClicked((DayView)((Object)object));
        }
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName((CharSequence)CalendarPagerView.class.getName());
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName((CharSequence)CalendarPagerView.class.getName());
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        int n5 = this.getChildCount();
        n3 = 0;
        n = 0;
        for (n2 = 0; n2 < n5; ++n2) {
            View view = this.getChildAt(n2);
            n4 = view.getMeasuredWidth();
            int n6 = view.getMeasuredHeight();
            view.layout(n, n3, n + n4, n3 + n6);
            n += n4;
            n4 = n3;
            if (n2 % 7 == 6) {
                n = 0;
                n4 = n3 + n6;
            }
            n3 = n4;
        }
    }

    protected void onMeasure(int n, int n2) {
        int n3 = View.MeasureSpec.getSize((int)n);
        int n4 = View.MeasureSpec.getMode((int)n);
        n = View.MeasureSpec.getSize((int)n2);
        if (View.MeasureSpec.getMode((int)n2) == 0 || n4 == 0) {
            throw new IllegalStateException("CalendarPagerView should never be left to decide it's size");
        }
        n2 = n3 / 7;
        n4 = n / this.getRows();
        this.setMeasuredDimension(n3, n);
        n3 = this.getChildCount();
        for (n = 0; n < n3; ++n) {
            this.getChildAt(n).measure(View.MeasureSpec.makeMeasureSpec((int)n2, (int)1073741824), View.MeasureSpec.makeMeasureSpec((int)n4, (int)1073741824));
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected Calendar resetAndGetWorkingCalendar() {
        boolean bl = true;
        this.getFirstViewDay().copyTo(tempWorkingCalendar);
        tempWorkingCalendar.setFirstDayOfWeek(this.getFirstDayOfWeek());
        int n = CalendarUtils.getDayOfWeek(tempWorkingCalendar);
        int n2 = this.getFirstDayOfWeek() - n;
        if (MaterialCalendarView.showOtherMonths(this.showOtherDates)) {
            if (n2 < 0) {
                bl = false;
            }
        } else if (n2 <= 0) {
            bl = false;
        }
        n = n2;
        if (bl) {
            n = n2 - 7;
        }
        tempWorkingCalendar.add(5, n);
        return tempWorkingCalendar;
    }

    public void setDateTextAppearance(int n) {
        Iterator<DayView> iterator = this.dayViews.iterator();
        while (iterator.hasNext()) {
            iterator.next().setTextAppearance(this.getContext(), n);
        }
    }

    public void setDayFormatter(DayFormatter dayFormatter) {
        Iterator<DayView> iterator = this.dayViews.iterator();
        while (iterator.hasNext()) {
            iterator.next().setDayFormatter(dayFormatter);
        }
    }

    void setDayViewDecorators(List<DecoratorResult> list) {
        this.decoratorResults.clear();
        if (list != null) {
            this.decoratorResults.addAll(list);
        }
        this.invalidateDecorators();
    }

    public void setMaximumDate(CalendarDay calendarDay) {
        this.maxDate = calendarDay;
        this.updateUi();
    }

    public void setMinimumDate(CalendarDay calendarDay) {
        this.minDate = calendarDay;
        this.updateUi();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setSelectedDates(Collection<CalendarDay> collection) {
        Iterator<DayView> iterator = this.dayViews.iterator();
        do {
            if (!iterator.hasNext()) {
                this.postInvalidate();
                return;
            }
            DayView dayView = iterator.next();
            CalendarDay calendarDay = dayView.getDate();
            boolean bl = collection != null && collection.contains(calendarDay);
            dayView.setChecked(bl);
        } while (true);
    }

    public void setSelectionColor(int n) {
        Iterator<DayView> iterator = this.dayViews.iterator();
        while (iterator.hasNext()) {
            iterator.next().setSelectionColor(n);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setSelectionEnabled(boolean bl) {
        Iterator<DayView> iterator = this.dayViews.iterator();
        while (iterator.hasNext()) {
            DayView dayView = iterator.next();
            CalendarPagerView calendarPagerView = bl ? this : null;
            dayView.setOnClickListener((View.OnClickListener)calendarPagerView);
            dayView.setClickable(bl);
        }
        return;
    }

    public void setShowOtherDates(int n) {
        this.showOtherDates = n;
        this.updateUi();
    }

    public void setWeekDayFormatter(WeekDayFormatter weekDayFormatter) {
        Iterator<WeekDayView> iterator = this.weekDayViews.iterator();
        while (iterator.hasNext()) {
            iterator.next().setWeekDayFormatter(weekDayFormatter);
        }
    }

    public void setWeekDayTextAppearance(int n) {
        Iterator<WeekDayView> iterator = this.weekDayViews.iterator();
        while (iterator.hasNext()) {
            iterator.next().setTextAppearance(this.getContext(), n);
        }
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }

    protected void updateUi() {
        for (DayView dayView : this.dayViews) {
            CalendarDay calendarDay = dayView.getDate();
            dayView.setupSelection(this.showOtherDates, calendarDay.isInRange(this.minDate, this.maxDate), this.isDayEnabled(calendarDay));
        }
        this.postInvalidate();
    }

    protected static class LayoutParams
    extends ViewGroup.MarginLayoutParams {
        public LayoutParams() {
            super(-2, -2);
        }
    }

}

