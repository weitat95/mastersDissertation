/*
 * Decompiled with CFR 0.147.
 */
package com.github.mikephil.charting.formatter;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.text.DecimalFormat;

public class DefaultValueFormatter
implements IValueFormatter {
    protected int mDecimalDigits;
    protected DecimalFormat mFormat;

    public DefaultValueFormatter(int n) {
        this.setup(n);
    }

    @Override
    public String getFormattedValue(float f, Entry entry, int n, ViewPortHandler viewPortHandler) {
        return this.mFormat.format(f);
    }

    public void setup(int n) {
        this.mDecimalDigits = n;
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < n; ++i) {
            if (i == 0) {
                stringBuffer.append(".");
            }
            stringBuffer.append("0");
        }
        this.mFormat = new DecimalFormat("###,###,###,##0" + stringBuffer.toString());
    }
}

