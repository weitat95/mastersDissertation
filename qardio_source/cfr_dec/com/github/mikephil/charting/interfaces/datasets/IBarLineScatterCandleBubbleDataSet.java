/*
 * Decompiled with CFR 0.147.
 */
package com.github.mikephil.charting.interfaces.datasets;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;

public interface IBarLineScatterCandleBubbleDataSet<T extends Entry>
extends IDataSet<T> {
    public int getHighLightColor();
}

