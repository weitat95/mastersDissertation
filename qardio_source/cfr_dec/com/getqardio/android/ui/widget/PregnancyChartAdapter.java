/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.database.DataSetObserver
 */
package com.getqardio.android.ui.widget;

import android.database.DataSetObserver;

public interface PregnancyChartAdapter {
    public float getMaxValue();

    public float getMinValue();

    public int getTrimesterColor(int var1);

    public String getTrimesterLabel(int var1);

    public float getValue(int var1);

    public String getValueLabel(int var1);

    public boolean hasValue(int var1);

    public void registerDataSetObserver(DataSetObserver var1);

    public void unregisterDataSetObserver(DataSetObserver var1);
}

