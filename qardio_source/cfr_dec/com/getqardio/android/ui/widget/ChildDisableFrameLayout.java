/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.widget.FrameLayout
 */
package com.getqardio.android.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class ChildDisableFrameLayout
extends FrameLayout {
    public ChildDisableFrameLayout(Context context) {
        super(context);
    }

    public ChildDisableFrameLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ChildDisableFrameLayout(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return !this.isEnabled();
    }
}

