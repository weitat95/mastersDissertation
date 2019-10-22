/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.ui.widget;

import com.getqardio.android.utils.Utils;

public abstract class MeasurementsChartAdapter {
    protected OnDataChangedListener onDataChangedListener;

    public abstract int getChartsCount();

    public abstract String[] getLabels(int var1);

    public abstract int getMaxValue();

    public abstract long getMeasurementDate(int var1);

    public abstract int getMeasuresCount();

    public abstract int getMinValue();

    public abstract int getValue(int var1, int var2);

    public String getValueLabel(int n, int n2) {
        synchronized (this) {
            String string2 = Utils.formatInteger(this.getValue(n, n2));
            return string2;
        }
    }

    public abstract int getValuesCount(int var1);

    public abstract boolean hasValue(int var1, int var2);

    public void setOnDataCountChangedListener(OnDataChangedListener onDataChangedListener) {
        this.onDataChangedListener = onDataChangedListener;
    }

    public static interface OnDataChangedListener {
        public void onDataChanged(int var1, int var2);
    }

}

