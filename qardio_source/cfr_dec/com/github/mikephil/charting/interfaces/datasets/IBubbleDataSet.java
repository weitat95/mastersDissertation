/*
 * Decompiled with CFR 0.147.
 */
package com.github.mikephil.charting.interfaces.datasets;

import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;

public interface IBubbleDataSet
extends IBarLineScatterCandleBubbleDataSet<BubbleEntry> {
    public float getHighlightCircleWidth();

    public float getMaxSize();

    public boolean isNormalizeSizeEnabled();
}

