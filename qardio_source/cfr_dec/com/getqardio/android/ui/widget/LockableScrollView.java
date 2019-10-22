/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.widget.ScrollView
 */
package com.getqardio.android.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class LockableScrollView
extends ScrollView {
    private boolean locked = true;

    public LockableScrollView(Context context) {
        super(context);
    }

    public LockableScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public LockableScrollView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    public void lock() {
        this.locked = true;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (this.locked) {
            return false;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.locked) {
            return false;
        }
        return super.onTouchEvent(motionEvent);
    }

    public void unlock() {
        this.locked = false;
    }
}

