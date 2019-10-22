/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 */
package com.getqardio.android.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import com.getqardio.android.ui.widget.HorizontalNumberPicker;

public class WeeksPicker
extends HorizontalNumberPicker {
    public WeeksPicker(Context context) {
        super(context);
    }

    public WeeksPicker(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public WeeksPicker(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    private void checkMinMaxValues(int n, int n2) {
        if (n2 < n) {
            throw new IllegalArgumentException("min value cannot be > max value");
        }
    }

    private void checkMinValueIsCorrect(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Min weeks count should be >=0");
        }
    }

    @Override
    public void setMinMaxValues(int n, int n2) {
        this.checkMinValueIsCorrect(n);
        this.checkMinMaxValues(n, n2);
        super.setMinMaxValues(n, n2);
    }
}

