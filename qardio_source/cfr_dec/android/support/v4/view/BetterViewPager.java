/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 */
package android.support.v4.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

public class BetterViewPager
extends ViewPager {
    public BetterViewPager(Context context) {
        super(context);
    }

    public BetterViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setChildrenDrawingOrderEnabledCompat(boolean bl) {
        this.setChildrenDrawingOrderEnabled(bl);
    }
}

