/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.TextView
 */
package com.getqardio.android.ui.widget.calendar.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CalendarRowView
extends ViewGroup {
    private int cellSize;
    private boolean isHeaderRow;

    public CalendarRowView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void addView(View view, int n, ViewGroup.LayoutParams layoutParams) {
        super.addView(view, n, layoutParams);
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        n3 = this.getChildCount();
        for (n = 0; n < n3; ++n) {
            this.getChildAt(n).layout(this.cellSize * n, 0, (n + 1) * this.cellSize, n4 - n2);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n, int n2) {
        int n3 = View.MeasureSpec.getSize((int)n);
        this.cellSize = n3 / 7;
        int n4 = View.MeasureSpec.makeMeasureSpec((int)this.cellSize, (int)1073741824);
        n = this.isHeaderRow ? View.MeasureSpec.makeMeasureSpec((int)this.cellSize, (int)Integer.MIN_VALUE) : n4;
        int n5 = 0;
        n2 = 0;
        int n6 = this.getChildCount();
        do {
            if (n2 >= n6) {
                this.setMeasuredDimension(this.getPaddingLeft() + n3 + this.getPaddingRight(), this.getPaddingTop() + n5 + this.getPaddingBottom());
                return;
            }
            View view = this.getChildAt(n2);
            view.measure(n4, n);
            int n7 = n5;
            if (view.getMeasuredHeight() > n5) {
                n7 = view.getMeasuredHeight();
            }
            ++n2;
            n5 = n7;
        } while (true);
    }

    public void setCellBackground(int n) {
        for (int i = 0; i < this.getChildCount(); ++i) {
            this.getChildAt(i).setBackgroundResource(n);
        }
    }

    public void setCellTextColor(int n) {
        for (int i = 0; i < this.getChildCount(); ++i) {
            ((TextView)this.getChildAt(i)).setTextColor(n);
        }
    }

    public void setCellTextColor(ColorStateList colorStateList) {
        for (int i = 0; i < this.getChildCount(); ++i) {
            ((TextView)this.getChildAt(i)).setTextColor(colorStateList);
        }
    }

    public void setIsHeaderRow(boolean bl) {
        this.isHeaderRow = bl;
    }
}

