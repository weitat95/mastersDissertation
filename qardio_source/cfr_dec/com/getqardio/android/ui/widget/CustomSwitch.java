/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 */
package com.getqardio.android.ui.widget;

import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;
import java.lang.reflect.Method;

public class CustomSwitch
extends SwitchCompat {
    private Method methodCancelPositionAnimator = null;
    private Method methodSetThumbPosition = null;

    public CustomSwitch(Context context) {
        super(context);
        this.init();
    }

    public CustomSwitch(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init();
    }

    public CustomSwitch(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.init();
    }

    private void init() {
        try {
            this.methodCancelPositionAnimator = SwitchCompat.class.getDeclaredMethod("cancelPositionAnimator", new Class[0]);
            this.methodSetThumbPosition = SwitchCompat.class.getDeclaredMethod("setThumbPosition", Float.TYPE);
            this.methodCancelPositionAnimator.setAccessible(true);
            this.methodSetThumbPosition.setAccessible(true);
            return;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            noSuchMethodException.printStackTrace();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void setChecked(boolean bl, boolean bl2) {
        int n = 1;
        super.setChecked(bl);
        if (bl2) return;
        bl = this.isChecked();
        try {
            if (this.methodCancelPositionAnimator == null) return;
            if (this.methodSetThumbPosition == null) return;
            this.methodCancelPositionAnimator.invoke((Object)this, new Object[0]);
            Method method = this.methodSetThumbPosition;
            if (!bl) {
                n = 0;
            }
            method.invoke((Object)this, n);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }
}

