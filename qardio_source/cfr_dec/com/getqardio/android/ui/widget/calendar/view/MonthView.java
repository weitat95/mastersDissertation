/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Typeface
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package com.getqardio.android.ui.widget.calendar.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.getqardio.android.ui.widget.calendar.entity.MonthCellEntity;
import com.getqardio.android.ui.widget.calendar.entity.MonthEntity;
import com.getqardio.android.ui.widget.calendar.view.CalendarCellView;
import com.getqardio.android.ui.widget.calendar.view.CalendarGridView;
import com.getqardio.android.ui.widget.calendar.view.CalendarRowView;
import com.getqardio.android.utils.Utils;
import com.getqardio.android.utils.ui.Convert;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MonthView
extends LinearLayout {
    private final String EMPTY_STRING;
    private final int MAX_ROWS_IN_MONTH;
    CalendarGridView grid;
    TextView title;

    public MonthView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.EMPTY_STRING = "";
        this.MAX_ROWS_IN_MONTH = 6;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static MonthView create(ViewGroup viewGroup, LayoutInflater object, DateFormat dateFormat, Calendar calendar, int n, int n2, int n3, int n4, int n5) {
        object = (MonthView)object.inflate(2130968653, viewGroup, false);
        viewGroup = viewGroup.getContext().getResources();
        ((MonthView)((Object)object)).setDividerColor(n);
        ((MonthView)((Object)object)).setDayTextColor(n3);
        ((MonthView)((Object)object)).setHeaderTextColor(n5);
        if (n2 != 0) {
            ((MonthView)((Object)object)).setDayBackground(n2);
        }
        n3 = calendar.get(7);
        n4 = calendar.getFirstDayOfWeek();
        LinearLayout linearLayout = (LinearLayout)object.findViewById(2131820966);
        n = 0;
        do {
            if (n >= 7) {
                calendar.set(7, n3);
                return object;
            }
            calendar.set(7, n4 + n);
            TextView textView = (TextView)linearLayout.getChildAt(n);
            n2 = calendar.get(7);
            n2 = n2 == 1 || n2 == 7 ? 2131689554 : 2131689556;
            textView.setTextColor(viewGroup.getColor(n2));
            textView.setTypeface(Typeface.DEFAULT);
            textView.setText((CharSequence)dateFormat.format(calendar.getTime()));
            ++n;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void init(MonthEntity object, List<List<MonthCellEntity>> list) {
        Locale locale = Utils.getLocale();
        this.title.setText((CharSequence)Convert.abbreviateMonthIfNecessary(Convert.changeLettersCaseIfNecessary(((MonthEntity)object).getLabel(), locale), locale));
        int n = list.size();
        this.grid.setNumRows(n);
        int n2 = 0;
        while (n2 < 6) {
            CalendarRowView calendarRowView = (CalendarRowView)this.grid.getChildAt(n2 + 1);
            if (n2 >= n) {
                calendarRowView.setVisibility(8);
            } else {
                calendarRowView.setVisibility(0);
                List<MonthCellEntity> list2 = list.get(n2);
                for (int i = 0; i < list2.size(); ++i) {
                    MonthCellEntity monthCellEntity = list2.get(i);
                    CalendarCellView calendarCellView = (CalendarCellView)calendarRowView.getChildAt(i);
                    object = monthCellEntity.isCurrentMonth() && !monthCellEntity.isHighlighted() ? Utils.formatInteger(monthCellEntity.getValue()) : "";
                    calendarCellView.setText((CharSequence)object);
                    calendarCellView.setEnabled(monthCellEntity.isCurrentMonth());
                    calendarCellView.setClickable(false);
                    calendarCellView.setCurrentMonth(monthCellEntity.isCurrentMonth());
                    calendarCellView.setToday(monthCellEntity.isToday());
                    calendarCellView.setHighlighted(monthCellEntity.isHighlighted());
                    boolean bl = monthCellEntity.isToday() && monthCellEntity.isHighlighted();
                    calendarCellView.setTodayHighlighted(bl);
                    calendarCellView.setTag((Object)monthCellEntity);
                }
            }
            ++n2;
        }
        return;
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.title = (TextView)this.findViewById(2131820567);
        this.grid = (CalendarGridView)this.findViewById(2131820967);
    }

    public void setDayBackground(int n) {
        this.grid.setDayBackground(n);
    }

    public void setDayTextColor(int n) {
        this.grid.setDayTextColor(n);
    }

    public void setDividerColor(int n) {
        this.grid.setDividerColor(n);
    }

    public void setHeaderTextColor(int n) {
        this.grid.setHeaderTextColor(n);
    }

    public void setTitleTextColor(int n) {
        this.title.setTextColor(n);
    }
}

