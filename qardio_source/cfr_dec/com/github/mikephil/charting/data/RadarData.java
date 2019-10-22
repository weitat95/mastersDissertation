/*
 * Decompiled with CFR 0.147.
 */
package com.github.mikephil.charting.data;

import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;

public class RadarData
extends ChartData<IRadarDataSet> {
    @Override
    public Entry getEntryForHighlight(Highlight highlight) {
        return ((IRadarDataSet)this.getDataSetByIndex(highlight.getDataSetIndex())).getEntryForIndex((int)highlight.getX());
    }
}

