/*
 * Decompiled with CFR 0.147.
 */
package com.github.mikephil.charting.data;

import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import java.util.List;

public class PieData
extends ChartData<IPieDataSet> {
    public IPieDataSet getDataSet() {
        return (IPieDataSet)this.mDataSets.get(0);
    }

    @Override
    public IPieDataSet getDataSetByIndex(int n) {
        if (n == 0) {
            return this.getDataSet();
        }
        return null;
    }

    @Override
    public Entry getEntryForHighlight(Highlight highlight) {
        return this.getDataSet().getEntryForIndex((int)highlight.getX());
    }

    public float getYValueSum() {
        float f = 0.0f;
        for (int i = 0; i < this.getDataSet().getEntryCount(); ++i) {
            f += ((PieEntry)this.getDataSet().getEntryForIndex(i)).getY();
        }
        return f;
    }
}

