/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.MotionEvent
 */
package com.prolificinteractive.materialcalendarview;

import android.content.Context;
import android.support.v4.view.BetterViewPager;
import android.view.MotionEvent;

class CalendarPager
extends BetterViewPager {
    private boolean pagingEnabled = true;

    public CalendarPager(Context context) {
        super(context);
    }

    @Override
    public boolean canScrollHorizontally(int n) {
        return this.pagingEnabled && super.canScrollHorizontally(n);
    }

    public boolean canScrollVertically(int n) {
        return this.pagingEnabled && super.canScrollVertically(n);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return this.pagingEnabled && super.onInterceptTouchEvent(motionEvent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.pagingEnabled && super.onTouchEvent(motionEvent);
    }

    public void setPagingEnabled(boolean bl) {
        this.pagingEnabled = bl;
    }
}

