/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class ButtonBarLayout
extends LinearLayout {
    private boolean mAllowStacking;
    private int mLastWidthSize;
    private int mMinimumHeight;

    public ButtonBarLayout(Context context, AttributeSet attributeSet) {
        boolean bl = false;
        super(context, attributeSet);
        this.mLastWidthSize = -1;
        this.mMinimumHeight = 0;
        if (this.getResources().getConfiguration().screenHeightDp >= 320) {
            bl = true;
        }
        context = context.obtainStyledAttributes(attributeSet, R.styleable.ButtonBarLayout);
        this.mAllowStacking = context.getBoolean(R.styleable.ButtonBarLayout_allowStacking, bl);
        context.recycle();
    }

    private int getNextVisibleChildIndex(int n) {
        int n2 = this.getChildCount();
        while (n < n2) {
            if (this.getChildAt(n).getVisibility() == 0) {
                return n;
            }
            ++n;
        }
        return -1;
    }

    private boolean isStacked() {
        return this.getOrientation() == 1;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setStacked(boolean bl) {
        int n = bl ? 1 : 0;
        this.setOrientation(n);
        n = bl ? 5 : 80;
        this.setGravity(n);
        View view = this.findViewById(R.id.spacer);
        if (view != null) {
            n = bl ? 8 : 4;
            view.setVisibility(n);
        }
        n = this.getChildCount() - 2;
        while (n >= 0) {
            this.bringChildToFront(this.getChildAt(n));
            --n;
        }
        return;
    }

    public int getMinimumHeight() {
        return Math.max(this.mMinimumHeight, super.getMinimumHeight());
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onMeasure(int n, int n2) {
        int n3 = View.MeasureSpec.getSize((int)n);
        if (this.mAllowStacking) {
            if (n3 > this.mLastWidthSize && this.isStacked()) {
                this.setStacked(false);
            }
            this.mLastWidthSize = n3;
        }
        int n4 = 0;
        if (!this.isStacked() && View.MeasureSpec.getMode((int)n) == 1073741824) {
            n3 = View.MeasureSpec.makeMeasureSpec((int)n3, (int)Integer.MIN_VALUE);
            n4 = 1;
        } else {
            n3 = n;
        }
        super.onMeasure(n3, n2);
        int n5 = n4;
        if (this.mAllowStacking) {
            n5 = n4;
            if (!this.isStacked()) {
                n3 = (this.getMeasuredWidthAndState() & 0xFF000000) == 16777216 ? 1 : 0;
                n5 = n4;
                if (n3 != 0) {
                    this.setStacked(true);
                    n5 = 1;
                }
            }
        }
        if (n5 != 0) {
            super.onMeasure(n, n2);
        }
        n = 0;
        n4 = this.getNextVisibleChildIndex(0);
        if (n4 >= 0) {
            View view = this.getChildAt(n4);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)view.getLayoutParams();
            n2 = 0 + (this.getPaddingTop() + view.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin);
            if (this.isStacked()) {
                n4 = this.getNextVisibleChildIndex(n4 + 1);
                n = n2;
                if (n4 >= 0) {
                    n = n2 + (this.getChildAt(n4).getPaddingTop() + (int)(16.0f * this.getResources().getDisplayMetrics().density));
                }
            } else {
                n = n2 + this.getPaddingBottom();
            }
        }
        if (ViewCompat.getMinimumHeight((View)this) != n) {
            this.setMinimumHeight(n);
        }
    }

    public void setAllowStacking(boolean bl) {
        if (this.mAllowStacking != bl) {
            this.mAllowStacking = bl;
            if (!this.mAllowStacking && this.getOrientation() == 1) {
                this.setStacked(false);
            }
            this.requestLayout();
        }
    }
}

