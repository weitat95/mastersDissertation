/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 */
package com.getqardio.android.ui.widget.calendar.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.getqardio.android.ui.widget.calendar.view.CalendarRowView;

public class CalendarGridView
extends ViewGroup {
    private final Paint dividerPaint = new Paint();
    private int oldNumRows;
    private int oldWidthMeasureSize;

    public CalendarGridView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.dividerPaint.setColor(this.getResources().getColor(2131689554));
        this.dividerPaint.setStrokeWidth(this.getResources().getDimension(2131427433));
    }

    public void addView(View view, int n, ViewGroup.LayoutParams layoutParams) {
        if (this.getChildCount() == 0) {
            ((CalendarRowView)view).setIsHeaderRow(true);
        }
        super.addView(view, n, layoutParams);
    }

    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        ViewGroup viewGroup = (ViewGroup)this.getChildAt(1);
        int n = viewGroup.getTop();
        int n2 = this.getBottom();
        int n3 = viewGroup.getChildAt(0).getLeft() + this.getLeft();
        canvas.drawLine((float)n3, (float)n, (float)n3, (float)n2, this.dividerPaint);
        for (int i = 0; i < 7; ++i) {
            float f = (float)(viewGroup.getChildAt(i).getRight() + n3) - 0.5f;
            canvas.drawLine(f, (float)n, f, (float)n2, this.dividerPaint);
        }
    }

    protected boolean drawChild(Canvas canvas, View view, long l) {
        boolean bl = super.drawChild(canvas, view, l);
        int n = view.getBottom() - 1;
        int n2 = view.getTop() - 1;
        int n3 = view.getLeft() + this.getLeft();
        int n4 = view.getRight();
        canvas.drawLine((float)n3, 0.5f + (float)n, (float)(n4 - 2), 0.5f + (float)n, this.dividerPaint);
        canvas.drawLine((float)n3, 0.5f + (float)n2, (float)(n4 - 2), 0.5f + (float)n2, this.dividerPaint);
        return bl;
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        n4 = 0;
        int n5 = this.getChildCount();
        for (n2 = 0; n2 < n5; ++n2) {
            View view = this.getChildAt(n2);
            int n6 = view.getMeasuredHeight();
            view.layout(n, n4, n3, n4 + n6);
            n4 += n6;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n, int n2) {
        if (this.oldWidthMeasureSize == (n = View.MeasureSpec.getSize((int)n))) {
            this.setMeasuredDimension(this.getMeasuredWidth(), this.getMeasuredHeight());
            return;
        }
        this.oldWidthMeasureSize = n;
        int n3 = n / 7;
        int n4 = n3 * 7;
        n2 = 0;
        int n5 = View.MeasureSpec.makeMeasureSpec((int)n4, (int)1073741824);
        int n6 = View.MeasureSpec.makeMeasureSpec((int)n3, (int)1073741824);
        n = 0;
        int n7 = this.getChildCount();
        do {
            if (n >= n7) {
                this.setMeasuredDimension(n4 + 2, n2);
                return;
            }
            View view = this.getChildAt(n);
            int n8 = n2;
            if (view.getVisibility() == 0) {
                if (n == 0) {
                    this.measureChild(view, n5, View.MeasureSpec.makeMeasureSpec((int)n3, (int)Integer.MIN_VALUE));
                } else {
                    this.measureChild(view, n5, n6);
                }
                n8 = n2 + view.getMeasuredHeight();
            }
            ++n;
            n2 = n8;
        } while (true);
    }

    public void setDayBackground(int n) {
        for (int i = 1; i < this.getChildCount(); ++i) {
            ((CalendarRowView)this.getChildAt(i)).setCellBackground(n);
        }
    }

    public void setDayTextColor(int n) {
        for (int i = 0; i < this.getChildCount(); ++i) {
            ColorStateList colorStateList = this.getResources().getColorStateList(n);
            ((CalendarRowView)this.getChildAt(i)).setCellTextColor(colorStateList);
        }
    }

    public void setDividerColor(int n) {
        this.dividerPaint.setColor(n);
    }

    public void setHeaderTextColor(int n) {
        ((CalendarRowView)this.getChildAt(0)).setCellTextColor(n);
    }

    public void setNumRows(int n) {
        if (this.oldNumRows != n) {
            this.oldWidthMeasureSize = 0;
        }
        this.oldNumRows = n;
    }
}

