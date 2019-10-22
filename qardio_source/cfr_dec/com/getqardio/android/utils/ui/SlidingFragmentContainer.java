/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.Activity
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.Display
 *  android.view.WindowManager
 *  android.widget.RelativeLayout
 */
package com.getqardio.android.utils.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Display;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class SlidingFragmentContainer
extends RelativeLayout {
    public SlidingFragmentContainer(Context context) {
        super(context);
    }

    public SlidingFragmentContainer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SlidingFragmentContainer(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    @TargetApi(value=21)
    public SlidingFragmentContainer(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
    }

    public float getXFraction() {
        int n = ((Activity)this.getContext()).getWindowManager().getDefaultDisplay().getWidth();
        if (n == 0) {
            return 0.0f;
        }
        return this.getX() / (float)n;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setXFraction(float f) {
        int n = ((Activity)this.getContext()).getWindowManager().getDefaultDisplay().getWidth();
        f = n > 0 ? (float)n * f : 0.0f;
        this.setX(f);
    }
}

