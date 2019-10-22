/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 *  android.widget.ListAdapter
 *  android.widget.ListView
 */
package com.getqardio.android.ui.widget.calendar.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.getqardio.android.R;
import com.getqardio.android.ui.widget.calendar.entity.MonthCellEntity;
import com.getqardio.android.ui.widget.calendar.entity.MonthEntity;
import com.getqardio.android.ui.widget.calendar.view.MonthView;
import com.getqardio.android.utils.DateUtils;
import com.getqardio.android.utils.Utils;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class QardioCalendarView
extends ListView {
    private final MonthAdapter adapter;
    private int calendarBackground;
    private final List<List<List<MonthCellEntity>>> cells = new ArrayList<List<List<MonthCellEntity>>>();
    private int dayBackgroundResId;
    private int dayTextColorResId;
    private int dividerColor;
    private int headerTextColor;
    List<Calendar> highlightedCals;
    private Locale locale;
    private Calendar maxCal;
    private Calendar minCal;
    private Calendar monthCounter;
    private DateFormat monthNameFormat;
    final List<MonthEntity> months = new ArrayList<MonthEntity>();
    private int titleTextColor;
    private Calendar today;
    private DateFormat weekdayNameFormat;

    public QardioCalendarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.highlightedCals = new ArrayList<Calendar>();
        this.obtainAttributes(context, attributeSet);
        this.adapter = new MonthAdapter();
        this.setDivider(null);
        this.setDividerHeight(0);
        this.setBackgroundColor(this.calendarBackground);
        this.setCacheColorHint(this.calendarBackground);
        this.locale = Utils.getLocale();
        this.today = Calendar.getInstance(this.locale);
        this.minCal = Calendar.getInstance(this.locale);
        this.maxCal = Calendar.getInstance(this.locale);
        this.monthCounter = Calendar.getInstance(this.locale);
        this.monthNameFormat = new SimpleDateFormat(context.getString(2131362515), this.locale);
        this.weekdayNameFormat = new SimpleDateFormat(context.getString(2131362489), this.locale);
    }

    private void obtainAttributes(Context context, AttributeSet attributeSet) {
        Resources resources = context.getResources();
        context = context.obtainStyledAttributes(attributeSet, R.styleable.QardioCalendarView);
        this.calendarBackground = context.getColor(0, resources.getColor(2131689553));
        this.dividerColor = context.getColor(1, resources.getColor(2131689553));
        this.dayBackgroundResId = context.getResourceId(2, 2130837604);
        this.dayTextColorResId = context.getResourceId(3, 2131689646);
        this.titleTextColor = context.getColor(4, 2131689646);
        this.headerTextColor = context.getColor(5, 2131689558);
        context.recycle();
    }

    static void setMidnight(Calendar calendar) {
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
    }

    private void validateAndUpdate() {
        if (this.getAdapter() == null) {
            this.setAdapter((ListAdapter)this.adapter);
        }
        this.adapter.notifyDataSetChanged();
    }

    /*
     * Enabled aggressive block sorting
     */
    List<List<MonthCellEntity>> getMonthCells(MonthEntity monthEntity, Calendar cloneable, Calendar calendar) {
        int n;
        Calendar calendar2 = Calendar.getInstance(this.locale);
        calendar2.setTime(((Calendar)cloneable).getTime());
        cloneable = new ArrayList();
        calendar2.set(5, 1);
        int n2 = calendar2.get(7);
        n2 = n = calendar2.getFirstDayOfWeek() - n2;
        if (n > 0) {
            n2 = n - 7;
        }
        calendar2.add(5, n2);
        n2 = calendar.get(2) == monthEntity.getMonth() ? 1 : 0;
        Calendar calendar3 = (Calendar)calendar.clone();
        calendar3.set(7, 7);
        block0 : while (!(calendar2.get(2) >= monthEntity.getMonth() + 1 && calendar2.get(1) >= monthEntity.getYear() || calendar2.get(1) > monthEntity.getYear() || n2 != 0 && calendar2.after(calendar3))) {
            ArrayList<MonthCellEntity> arrayList = new ArrayList<MonthCellEntity>();
            cloneable.add(0, arrayList);
            n = 0;
            do {
                if (n >= 7) continue block0;
                Date date = calendar2.getTime();
                boolean bl = calendar2.get(2) == monthEntity.getMonth();
                boolean bl2 = DateUtils.sameDate(calendar2, calendar);
                boolean bl3 = bl && DateUtils.containsDate(this.highlightedCals, calendar2);
                arrayList.add(new MonthCellEntity(date, bl, bl2, bl3, calendar2.get(5)));
                calendar2.add(5, 1);
                ++n;
            } while (true);
            break;
        }
        return cloneable;
    }

    public void init(Date date, List<Calendar> list) {
        this.init(date, this.locale, list);
    }

    public void init(Date object, Locale locale, List<Calendar> list) {
        if (object == null) {
            throw new IllegalArgumentException("minDate and maxDate must be non-null.  ");
        }
        if (((Date)object).getTime() == 0L) {
            throw new IllegalArgumentException("minDate and maxDate must be non-zero.  ");
        }
        if (locale == null) {
            throw new IllegalArgumentException("Locale is null.");
        }
        Date date = DateUtils.getMinDate((Date)object, list);
        this.locale = locale;
        this.today = Calendar.getInstance(locale);
        this.minCal = Calendar.getInstance(locale);
        this.maxCal = Calendar.getInstance(locale);
        this.monthCounter = Calendar.getInstance(locale);
        this.monthNameFormat = new SimpleDateFormat(this.getContext().getString(2131362515), locale);
        for (MonthEntity monthEntity : this.months) {
            monthEntity.setLabel(this.monthNameFormat.format(monthEntity.getDate()));
        }
        this.weekdayNameFormat = new SimpleDateFormat(this.getContext().getString(2131362489), locale);
        this.highlightedCals = list;
        this.cells.clear();
        this.months.clear();
        this.minCal.setTime((Date)object);
        this.maxCal.setTime(date);
        QardioCalendarView.setMidnight(this.minCal);
        QardioCalendarView.setMidnight(this.maxCal);
        this.monthCounter.setTime(this.minCal.getTime());
        int n = this.maxCal.get(2);
        int n2 = this.maxCal.get(1);
        while ((this.monthCounter.get(2) >= n || this.monthCounter.get(1) > n2) && this.monthCounter.get(1) > n2 - 1) {
            object = this.monthCounter.getTime();
            object = new MonthEntity(this.monthCounter.get(2), this.monthCounter.get(1), (Date)object, this.monthNameFormat.format((Date)object));
            this.cells.add(this.getMonthCells((MonthEntity)object, this.monthCounter, this.today));
            this.months.add((MonthEntity)object);
            this.monthCounter.add(2, -1);
        }
        this.validateAndUpdate();
    }

    protected void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
    }

    private class MonthAdapter
    extends BaseAdapter {
        private final LayoutInflater inflater;

        private MonthAdapter() {
            this.inflater = LayoutInflater.from((Context)QardioCalendarView.this.getContext());
        }

        public int getCount() {
            return QardioCalendarView.this.months.size();
        }

        public Object getItem(int n) {
            return QardioCalendarView.this.months.get(n);
        }

        public long getItemId(int n) {
            return n;
        }

        public View getView(int n, View object, ViewGroup viewGroup) {
            MonthView monthView = (MonthView)((Object)object);
            object = monthView;
            if (monthView == null) {
                object = MonthView.create(viewGroup, this.inflater, QardioCalendarView.this.weekdayNameFormat, QardioCalendarView.this.today, QardioCalendarView.this.dividerColor, QardioCalendarView.this.dayBackgroundResId, QardioCalendarView.this.dayTextColorResId, QardioCalendarView.this.titleTextColor, QardioCalendarView.this.headerTextColor);
            }
            object.init(QardioCalendarView.this.months.get(n), (List)QardioCalendarView.this.cells.get(n));
            return object;
        }

        public boolean isEnabled(int n) {
            return false;
        }
    }

}

