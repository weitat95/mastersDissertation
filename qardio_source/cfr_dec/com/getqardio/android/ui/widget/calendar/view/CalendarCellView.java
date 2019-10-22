/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Typeface
 *  android.util.AttributeSet
 */
package com.getqardio.android.ui.widget.calendar.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class CalendarCellView
extends AppCompatTextView {
    private static final int[] STATE_CURRENT_MONTH = new int[]{2130772555};
    private static final int[] STATE_HIGHLIGHTED;
    private static final int[] STATE_TODAY;
    private static final int[] STATE_TODAY_HIGHLIGHTED;
    private boolean isCurrentMonth = false;
    private boolean isHighlighted = false;
    private boolean isToday = false;
    private boolean isTodayHighlighted = false;

    static {
        STATE_TODAY = new int[]{2130772556};
        STATE_HIGHLIGHTED = new int[]{2130772557};
        STATE_TODAY_HIGHLIGHTED = new int[]{2130772558};
    }

    public CalendarCellView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.setTypeface(Typeface.DEFAULT);
    }

    protected int[] onCreateDrawableState(int n) {
        int[] arrn = super.onCreateDrawableState(n + 5);
        if (this.isCurrentMonth) {
            CalendarCellView.mergeDrawableStates((int[])arrn, (int[])STATE_CURRENT_MONTH);
        }
        if (this.isToday && this.isCurrentMonth && !this.isTodayHighlighted) {
            CalendarCellView.mergeDrawableStates((int[])arrn, (int[])STATE_TODAY);
        }
        if (this.isHighlighted && !this.isTodayHighlighted) {
            CalendarCellView.mergeDrawableStates((int[])arrn, (int[])STATE_HIGHLIGHTED);
        }
        if (this.isTodayHighlighted) {
            CalendarCellView.mergeDrawableStates((int[])arrn, (int[])STATE_TODAY_HIGHLIGHTED);
        }
        return arrn;
    }

    public void setCurrentMonth(boolean bl) {
        this.isCurrentMonth = bl;
        this.refreshDrawableState();
    }

    public void setHighlighted(boolean bl) {
        this.isHighlighted = bl;
        this.refreshDrawableState();
    }

    public void setToday(boolean bl) {
        this.isToday = bl;
        this.refreshDrawableState();
    }

    public void setTodayHighlighted(boolean bl) {
        this.isTodayHighlighted = bl;
        this.refreshDrawableState();
    }
}

