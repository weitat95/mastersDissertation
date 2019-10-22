/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 */
package com.getqardio.android.ui.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

public class CustomViewPager
extends ViewPager {
    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
        int n3 = 0;
        for (n2 = 0; n2 < this.getChildCount(); ++n2) {
            View view = this.getChildAt(n2);
            view.measure(n, View.MeasureSpec.makeMeasureSpec((int)0, (int)0));
            int n4 = view.getMeasuredHeight();
            int n5 = n3;
            if (n4 > n3) {
                n5 = n4;
            }
            n3 = n5;
        }
        super.onMeasure(n, View.MeasureSpec.makeMeasureSpec((int)n3, (int)1073741824));
    }
}

