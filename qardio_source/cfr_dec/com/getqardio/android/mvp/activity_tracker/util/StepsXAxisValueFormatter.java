/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.activity_tracker.util;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

public class StepsXAxisValueFormatter
implements IAxisValueFormatter {
    private boolean[] hasData;

    public StepsXAxisValueFormatter(boolean[] arrbl) {
        this.hasData = arrbl;
    }

    @Override
    public String getFormattedValue(float f, AxisBase axisBase) {
        int n = (int)f;
        if (n >= 0 && n < this.hasData.length && this.hasData[n]) {
            return "0";
        }
        return "1";
    }
}

