/*
 * Decompiled with CFR 0.147.
 */
package com.github.mikephil.charting.formatter;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import java.text.DecimalFormat;

public class DefaultAxisValueFormatter
implements IAxisValueFormatter {
    protected int digits = 0;
    protected DecimalFormat mFormat;

    public DefaultAxisValueFormatter(int n) {
        this.digits = n;
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < n; ++i) {
            if (i == 0) {
                stringBuffer.append(".");
            }
            stringBuffer.append("0");
        }
        this.mFormat = new DecimalFormat("###,###,###,##0" + stringBuffer.toString());
    }

    public int getDecimalDigits() {
        return this.digits;
    }

    @Override
    public String getFormattedValue(float f, AxisBase axisBase) {
        return this.mFormat.format(f);
    }
}

