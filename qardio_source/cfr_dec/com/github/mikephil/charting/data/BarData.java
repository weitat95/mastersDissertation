/*
 * Decompiled with CFR 0.147.
 */
package com.github.mikephil.charting.data;

import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;

public class BarData
extends BarLineScatterCandleBubbleData<IBarDataSet> {
    private float mBarWidth = 0.85f;

    public BarData() {
    }

    public BarData(IBarDataSet ... arriBarDataSet) {
        super((IBarLineScatterCandleBubbleDataSet[])arriBarDataSet);
    }

    public float getBarWidth() {
        return this.mBarWidth;
    }

    public void setBarWidth(float f) {
        this.mBarWidth = f;
    }
}

